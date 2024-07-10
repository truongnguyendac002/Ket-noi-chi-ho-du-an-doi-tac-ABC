package com.msb.insurance.pob.service.impl;

import com.msb.insurance.pob.TestAccount.Account;
import com.msb.insurance.pob.TestAccount.AccountService;
import com.msb.insurance.pob.common.Constant;
import com.msb.insurance.pob.common.GsonUtil;
import com.msb.insurance.pob.common.PobErrorRequest;
import com.msb.insurance.pob.exception.PobTransactionException;
import com.msb.insurance.pob.model.request.TransactionRequest;
import com.msb.insurance.pob.model.response.RespMessage;
import com.msb.insurance.pob.model.response.ack.AckBatchDetailResponse;
import com.msb.insurance.pob.model.response.ack.AckBatchResponse;
import com.msb.insurance.pob.model.response.ack.AckTransactionResponse;
import com.msb.insurance.pob.model.response.put.PutBatchResponse;
import com.msb.insurance.pob.model.response.put.PutTransactionResponse;
import com.msb.insurance.pob.repository.entity.BatchDetail;
import com.msb.insurance.pob.repository.entity.SercBatchInfo;
import com.msb.insurance.pob.repository.entity.Transaction;
import com.msb.insurance.pob.repository.jpa.BatchDetailRepository;
import com.msb.insurance.pob.repository.jpa.BatchRepository;
import com.msb.insurance.pob.repository.jpa.TransactionRepository;
import com.msb.insurance.pob.service.ITransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TransactionServiceImpl implements ITransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private AccountService accountService;

    public String saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction).toString();

    }

    @Override
    public Boolean existsByMsgId(String msgId) {
        return transactionRepository.existsByMsgId(msgId);
    }


    public ResponseEntity<String> ackProcess(TransactionRequest request) {
        try {
            preHandleAckRequest(request);
            AckTransactionResponse data = ackProcessExc(request);
            RespMessage resp = new RespMessage(Constant.Success.name(), Constant.Success.getRespDesc(), data);
            return new ResponseEntity<>(GsonUtil.getInstance().toJson(resp), HttpStatus.OK);
        } catch (PobTransactionException e) {
            RespMessage resp = new RespMessage(e.getCode(), e.getDesc());
            String result = GsonUtil.getInstance().toJson(resp);
            log.info("RespMessage : " + result);
            return new ResponseEntity<>(GsonUtil.getInstance().toJson(resp), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            RespMessage resp = new RespMessage(Constant.Internal_Server_Error.name(), Constant.Internal_Server_Error.getRespDesc());
            String result = GsonUtil.getInstance().toJson(resp);
            log.info("RespMessage : " + result);
            return new ResponseEntity<>(GsonUtil.getInstance().toJson(resp), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private AckTransactionResponse ackProcessExc(TransactionRequest request) {
        AckTransactionResponse resp = new AckTransactionResponse();
        resp.setMsgId(request.getMsgId());
        resp.setPartnerCode(request.getPartnerCode());
        resp.setSercBatchInfo(initAckBatchResponse(request.getSercBatchInfo()));
        resp.setSignature(request.getSignature());
        return resp;
    }

    private AckBatchResponse initAckBatchResponse(SercBatchInfo sercBatchInfo) {
        AckBatchResponse ackBatchResponse = new AckBatchResponse();
        BatchRepository batchRepository = context.getBean(BatchRepository.class);
        Optional<SercBatchInfo> op = batchRepository.findById(sercBatchInfo.getBatchId());
        if (op.isEmpty()) {
            throw new PobTransactionException(PobErrorRequest.Fail);
        }
        SercBatchInfo batch = op.get();
        ackBatchResponse.setBatchId(batch.getBatchId());
        ackBatchResponse.setQuantity(batch.getQuantity());
        ackBatchResponse.setRequestTime(batch.getRequestTime());
        ackBatchResponse.setStatus(batch.getStatus());
        List<AckBatchDetailResponse> sercBatchDetails = new ArrayList<>();

        //Validate sid
        List<String> sIdList = Optional.of(batch)
                .map(SercBatchInfo::getSercBatchDetails)
                .map(details -> details.stream()
                        .map(BatchDetail::getSId)
                        .collect(Collectors.toList()))
                .orElse(null);
        for (BatchDetail batchDetail : sercBatchInfo.getSercBatchDetails()) {
            AckBatchDetailResponse ackBatchDetail = initAckBatchDetailResponse(batchDetail);
            if (!sIdList.contains(ackBatchDetail.getSId())) {
                throw new PobTransactionException(PobErrorRequest.Fail);
            }
            sercBatchDetails.add(ackBatchDetail);
        }
        ackBatchResponse.setSercBatchDetails(sercBatchDetails);
        return ackBatchResponse;
    }

    private AckBatchDetailResponse initAckBatchDetailResponse(BatchDetail batchDetail) {
        AckBatchDetailResponse ackBatchDetailResponse = new AckBatchDetailResponse();
        BatchDetailRepository batchDetailRepository = context.getBean(BatchDetailRepository.class);
        Optional<BatchDetail> op = batchDetailRepository.findById(batchDetail.getSId());
        if (op.isEmpty()) {
            throw new PobTransactionException(PobErrorRequest.Fail);
        }
        BatchDetail detail = op.get();
        ackBatchDetailResponse.setSId(detail.getSId());
        ackBatchDetailResponse.setStatus(detail.getStatus());
        return ackBatchDetailResponse;
    }

    public void preHandleAckRequest(TransactionRequest request) {
        if (!verifySignature(request)) {
            throw new PobTransactionException(PobErrorRequest.Sign_Invalid);
        }
        String msgId = Optional.of(request).map(TransactionRequest::getMsgId).orElse(null);
        if (!StringUtils.hasText(msgId)) {
            throw new PobTransactionException(PobErrorRequest.Fail);
        }
        String partnerCode = Optional.of(request).map(TransactionRequest::getPartnerCode).orElse(null);
        if (!StringUtils.hasText(partnerCode)) {
            throw new PobTransactionException(PobErrorRequest.Fail);
        }
        SercBatchInfo sercBatchInfo = Optional.of(request).map(TransactionRequest::getSercBatchInfo).orElse(null);
        if (sercBatchInfo == null) {
            throw new PobTransactionException(PobErrorRequest.Fail);
        }
        String batchId = Optional.of(sercBatchInfo).map(SercBatchInfo::getBatchId).orElse(null);
        if (!StringUtils.hasText(batchId)) {
            throw new PobTransactionException(PobErrorRequest.Fail);
        }
        int quantity = Optional.of(sercBatchInfo).map(SercBatchInfo::getQuantity).orElse(0);
        if (quantity <= 0) {
            throw new PobTransactionException(PobErrorRequest.Fail);
        }
        String requestTime = Optional.of(sercBatchInfo).map(SercBatchInfo::getRequestTime).orElse(null);
        if (!StringUtils.hasText(requestTime)) {
            throw new PobTransactionException(PobErrorRequest.Fail);
        }
        List<BatchDetail> batchDetails = Optional.of(sercBatchInfo).map(SercBatchInfo::getSercBatchDetails).orElse(null);
        if (batchDetails.size() > 100) {
            throw new PobTransactionException(PobErrorRequest.Big_Transaction);
        }
    }

    private boolean verifySignature(TransactionRequest request) {
        return true;
    }



    //xu ly put action
    public ResponseEntity<?> putProcess(TransactionRequest request) {
        try {
            preHandlePutRequest(request);
            Transaction transaction = new Transaction();
            List<BatchDetail> batchDetailList = request.getSercBatchInfo().getSercBatchDetails();
            List<Account> accountList = accountService.getAll();
            if (checkMatchingAccount(batchDetailList,accountList) == true){
                transaction.setMsgId(request.getMsgId());
                transaction.setPartnerCode(request.getPartnerCode());
                transaction.setSignature(request.getSignature());
                transaction.setSercBathInfo(request.getSercBatchInfo());
                transaction.setSignature(request.getSignature());
            }else {
                throw new PobTransactionException(PobErrorRequest.Fail);
            }
            saveTransaction(transaction);
            PutTransactionResponse data = putProcessExc(request);
//            RespMessage resp = new RespMessage(Constant.Success.name(), Constant.Success.getRespDesc(), data);
            return new ResponseEntity<>(new RespMessage(Constant.Success.name(), Constant.Success.getRespDesc(), data), HttpStatus.OK);
        } catch (PobTransactionException e) {
            RespMessage resp = new RespMessage(e.getCode(), e.getDesc());
            String result = GsonUtil.getInstance().toJson(resp);
            log.info("RespMessage : " + result);
            return new ResponseEntity<>(GsonUtil.getInstance().toJson(resp), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            RespMessage resp = new RespMessage(Constant.Internal_Server_Error.name(), Constant.Internal_Server_Error.getRespDesc());
            String result = GsonUtil.getInstance().toJson(resp);
            log.info("RespMessage : " + result);
            return new ResponseEntity<>(GsonUtil.getInstance().toJson(resp), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
     public PutTransactionResponse putProcessExc(TransactionRequest request){
        PutTransactionResponse resp = new PutTransactionResponse();
        resp.setMsgId(request.getMsgId());
        resp.setPartnerCode(request.getPartnerCode());
        resp.setSignature(request.getSignature());
        resp.setSercBatchInfo(initPutBatchResponse(request));
        return resp;
     }
    public PutBatchResponse initPutBatchResponse(TransactionRequest request){
        PutBatchResponse response = new PutBatchResponse();
        response.setBatchId(request.getSercBatchInfo().getBatchId());
        response.setQuantity(request.getSercBatchInfo().getQuantity());
        response.setRequestTime(request.getSercBatchInfo().getRequestTime());
        response.setTotalAmount(request.getSercBatchInfo().getTotalAmount());
        response.setStatus(2);
        return response;
    }

    public void preHandlePutRequest(TransactionRequest request) {
        if (existsByMsgId(request.getMsgId())){
            throw new PobTransactionException(PobErrorRequest.Fail);
        }
        String msgId = Optional.of(request).map(TransactionRequest::getMsgId).orElse(null);
        if (!StringUtils.hasText(msgId)) {
            throw new PobTransactionException(PobErrorRequest.Fail);
        }
        SercBatchInfo sercBatchInfo = Optional.of(request).map(TransactionRequest::getSercBatchInfo).orElse(null);
        if (sercBatchInfo == null) {
            throw new PobTransactionException(PobErrorRequest.Fail);
        }
        String batchId = Optional.of(sercBatchInfo).map(SercBatchInfo::getBatchId).orElse(null);
        if (!StringUtils.hasText(batchId)) {
            throw new PobTransactionException(PobErrorRequest.Fail);
        }
        int quantity = Optional.of(sercBatchInfo).map(SercBatchInfo::getQuantity).orElse(0);
        if (quantity <= 0) {
            throw new PobTransactionException(PobErrorRequest.Fail);
        }
        List<BatchDetail> batchDetails = Optional.of(sercBatchInfo).map(SercBatchInfo::getSercBatchDetails).orElse(null);
        if (batchDetails.size() > 100) {
            throw new PobTransactionException(PobErrorRequest.Big_Transaction);
        }
    }
    public boolean checkMatchingAccount(List<BatchDetail> batchDetailList, List<Account> accountList) {
        boolean foundMatchingAccount = false;
        for (BatchDetail batchDetail : batchDetailList) {
            for (Account account : accountList) {
                if (batchDetail.getCAccount().equals(account.getCAccount()) && batchDetail.getCName().equals(account.getCName())) {
                    foundMatchingAccount = true;
                    return true;
                }
            }
        }
        return false;
    }
}


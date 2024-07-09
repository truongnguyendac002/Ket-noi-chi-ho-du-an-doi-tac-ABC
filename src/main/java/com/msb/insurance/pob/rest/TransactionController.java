package com.msb.insurance.pob.rest;

import com.msb.insurance.pob.TestAccount.Account;
import com.msb.insurance.pob.TestAccount.AccountService;
import com.msb.insurance.pob.common.PobErrorRequest;
import com.msb.insurance.pob.model.TransactionDto;
import com.msb.insurance.pob.model.TransactionMapper;
import com.msb.insurance.pob.repository.entity.BatchDetail;
import com.msb.insurance.pob.repository.entity.Transaction;
import com.msb.insurance.pob.model.request.TransactionRequest;
import com.msb.insurance.pob.model.response.TransactionResponse;
import com.msb.insurance.pob.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pob/transaction")
public class TransactionController {
    private String resCode;
    private String resDes;
    @Autowired
    private TransactionServiceImpl transactionServiceImpl;
    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionMapper transactionMapper;

    @RequestMapping(value = "/put", method = RequestMethod.POST)
    public ResponseEntity<?> putTransaction(@RequestBody TransactionRequest request){
        //check msgId
        if (transactionServiceImpl.existsByMsgId(request.getMsgId())){
            resCode = PobErrorRequest.Fail.getRespCode();
            resDes = PobErrorRequest.Fail.getRespDesc();
            return ResponseEntity.badRequest().body(new TransactionResponse(resCode, resDes));
        }
        List<BatchDetail> batchDetailList = request.getSercBatchInfo().getSercBatchDetails();
        List<Account> accountList = accountService.getAll();
        Transaction transaction = new Transaction();
        //check list batch detail
        if (batchDetailList.size() > 2){
            resCode = PobErrorRequest.Big_Transaction.getRespCode();
            resDes = PobErrorRequest.Big_Transaction.getRespDesc();
            return ResponseEntity.badRequest().body(new TransactionResponse(resCode, resDes));
        }
        //check thong tin trong batch detail
        for (BatchDetail batchDetail : batchDetailList) {
            boolean foundMatchingAccount = false;
            for (Account account : accountList) {
                if (batchDetail.getCAccount().equals(account.getCAccount())) {
                    if (batchDetail.getCName().equals(account.getCName())){
                        // Nếu tìm thấy account khớp với batchDetail
                        transaction.setMsgId(request.getMsgId());
                        transaction.setPartnerCode(request.getPartnerCode());
                        transaction.setSercBathInfo(request.getSercBatchInfo());
                        transaction.setSignature(request.getSignature());
                        resCode = PobErrorRequest.Success.getRespCode();
                        resDes = PobErrorRequest.Success.getRespDesc();
                        foundMatchingAccount = true;
                        break;
                    }
                }
            }
            if (!foundMatchingAccount) {
                // Nếu không tìm thấy account khớp với batchDetail
                resCode = PobErrorRequest.Fail.getRespCode();
                resDes = PobErrorRequest.Fail.getRespDesc();
                return ResponseEntity.badRequest().body(new TransactionResponse(resCode, resDes));
            }
        }
        transactionServiceImpl.saveTransaction(transaction);
        TransactionDto transactionDto = transactionMapper.convertToDto(transaction);
        return ResponseEntity.ok(new TransactionResponse(resCode, resDes,transactionDto));
    }

    @RequestMapping(value = "/ack", method = RequestMethod.POST)
    public ResponseEntity<String> ackTransaction(@RequestBody TransactionRequest request){
        return transactionServiceImpl.ackProcess(request);
    }
}


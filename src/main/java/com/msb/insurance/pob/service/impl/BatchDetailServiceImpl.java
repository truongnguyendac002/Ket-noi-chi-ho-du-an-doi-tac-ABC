package com.msb.insurance.pob.service.impl;

import com.msb.insurance.pob.common.PobErrorTransaction;
import com.msb.insurance.pob.common.PobTransactionStatus;
import com.msb.insurance.pob.model.response.notification.UpdateBatchDetailRequest;
import com.msb.insurance.pob.repository.entity.BatchDetail;
import com.msb.insurance.pob.repository.jpa.BatchDetailRepository;
import com.msb.insurance.pob.service.IBatchDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BatchDetailServiceImpl implements IBatchDetailService {
    @Autowired
    BatchDetailRepository batchDetailRepository;

    private final String PARTNER_CODE = "ABC";
    @Override
    public List<UpdateBatchDetailRequest> scanPendingBatchDetail() {

        Optional<List<BatchDetail>> op = batchDetailRepository.findPendingBatchDetail();
        if (op.isEmpty()) {
            return null;
        }
        List<BatchDetail> batchDetailList = op.get();
        List<UpdateBatchDetailRequest> resultList = new ArrayList<>();
        for (BatchDetail batchDetail : batchDetailList) {
            PobErrorTransaction errorCode = preHandle(batchDetail);
            batchDetail.setStatus(errorCode.getCode());
            batchDetailRepository.saveAndFlush(batchDetail);
            resultList.add(initResponse(batchDetail));
        }

        return resultList;
    }
    UpdateBatchDetailRequest initResponse(BatchDetail batchDetail) {
        UpdateBatchDetailRequest response = new UpdateBatchDetailRequest();
        response.setMsgId(UUID.randomUUID().toString());
        response.setPartnerCode(PARTNER_CODE);
        response.setSId(batchDetail.getSId());

        String statusCode = batchDetail.getStatus();
        if (!statusCode.equals("0")){
            response.setStatus(PobTransactionStatus.Fail.getCode());
            Optional<PobErrorTransaction> opMessage = Arrays.stream(PobErrorTransaction.values())
                            .filter(e -> e.getCode().equals(statusCode))
                            .findFirst();
            response.setMessage(PobErrorTransaction.Fail.getDesc() + " " + opMessage.get().getDesc());
        } else {
            response.setStatus(PobTransactionStatus.Success.getCode());
            response.setMessage(PobTransactionStatus.Success.getDesc());
        }
        response.setSignature("signature");
        return response;
    }

    private PobErrorTransaction preHandle(BatchDetail batchDetail) {
        try {
            if (!checkSoDu(batchDetail.getCAccount())) {
                return PobErrorTransaction.Khong_du_so_du;
            }
            if (batchDetail.getSId().isEmpty()) {
                return PobErrorTransaction.SId_not_null;
            }
            if (!checkHoatDong(batchDetail.getCAccount())) {
                return PobErrorTransaction.Tk_khong_hd;
            }
            if (!checkAccountValid(batchDetail.getCAccount())) {
                return PobErrorTransaction.CAccount_invalid;
            }
            if (!checkAmountValid(batchDetail.getCAmount())) {
                return PobErrorTransaction.Amount_invalid;
            }
            if (!checkRecieverName(batchDetail.getCName())) {
                return PobErrorTransaction.Invalid_receiver_name;
            }
            if (!checkBankNostro(batchDetail.getBankNostro())) {
                return PobErrorTransaction.BankNostro_wrong;
            }
            if (!checkDuplicateSId(batchDetail.getSId())) {
                return PobErrorTransaction.SId_duplicated;
            }
            if (!checkSequence(batchDetail.getSId())) {
                return PobErrorTransaction.So_tt_trung_nhau;
            }
            if (!checkDateVerification(batchDetail.getSId())) {
                return PobErrorTransaction.Date_verification;
            }
            return PobErrorTransaction.Success;
        }
//        catch (TimeoutException e) {
//            return PobErrorTransaction.Timeout;
//        }
        catch (Exception e) {
            return PobErrorTransaction.Fail;
        }


    }

    private boolean checkSoDu(String cAccount) {
        return true;
    }

    private boolean checkHoatDong(String cAccount) {
        return true;
    }

    private boolean checkAccountValid(String cAccount) {
        return true;
    }

    private boolean checkAmountValid(long cAmount) {
        return true;
    }

    private boolean checkRecieverName(String cName) {
        return true;
    }

    private boolean checkBankNostro(String bankNostro) {
        return true;
    }

    private boolean checkDuplicateSId(String sId) {
        return batchDetailRepository.checkDuplicateSId(sId).isEmpty();
    }

    private boolean checkSequence(String sId) {
        return true;
    }

    private boolean checkDateVerification(String sId) {
        return true;
    }
}

package com.msb.insurance.pob.rest;

import com.msb.insurance.pob.model.Transaction;
import com.msb.insurance.pob.model.request.TransactionRequest;
import com.msb.insurance.pob.service.impl.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pob/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @RequestMapping(value = "/put", method = RequestMethod.POST)
    public ResponseEntity<String> putTransaction(@RequestBody TransactionRequest request){

        Transaction transaction = new Transaction();
        transaction.setMsgId(request.getMsgId());
        transaction.setPartnerCode(request.getPartnerCode());
        transaction.setSercBathInfo(request.getSercBatchInfo());
        transaction.setSignature(request.getSignature());
        String result = transactionService.saveTransaction(transaction);
        return ResponseEntity.ok(result);

//        return ResponseEntity.ok(""+ transaction);
    }
}

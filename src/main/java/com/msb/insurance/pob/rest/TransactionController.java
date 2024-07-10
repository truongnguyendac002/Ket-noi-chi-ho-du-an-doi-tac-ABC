package com.msb.insurance.pob.rest;

import com.msb.insurance.pob.model.request.TransactionRequest;
import com.msb.insurance.pob.service.ITransactionService;
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
    private ITransactionService transactionService;

    @RequestMapping(value = "/put", method = RequestMethod.POST)
    public ResponseEntity<?> putTransaction(@RequestBody TransactionRequest request){
        return transactionService.putProcess(request);
    }

    @RequestMapping(value = "/ack", method = RequestMethod.POST)
    public ResponseEntity<String> ackTransaction(@RequestBody TransactionRequest request){
        return transactionService.ackProcess(request);
    }
}


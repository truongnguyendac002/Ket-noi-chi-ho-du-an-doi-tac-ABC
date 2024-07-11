package com.msb.insurance.pob.service;

import com.msb.insurance.pob.model.request.TransactionRequest;
import com.msb.insurance.pob.model.response.RespMessage;
import org.springframework.http.ResponseEntity;

public interface ITransactionService {
    Boolean existsByMsgId(String msgId);
    ResponseEntity<String> ackProcess(TransactionRequest request);
    ResponseEntity<?> putProcess(TransactionRequest request);
    RespMessage scanPendingTransactions();
}

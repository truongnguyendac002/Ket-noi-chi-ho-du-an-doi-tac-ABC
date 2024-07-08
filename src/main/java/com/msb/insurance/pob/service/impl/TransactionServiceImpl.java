package com.msb.insurance.pob.service.impl;

import com.msb.insurance.pob.repository.entity.Transaction;
import com.msb.insurance.pob.repository.jpa.TransactionRepository;
import com.msb.insurance.pob.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements ITransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public String saveTransaction(Transaction transaction){
        return transactionRepository.save(transaction).toString();

    }
    @Override
    public Boolean existsByMsgId(String msgId) {
        return transactionRepository.existsByMsgId(msgId);
    }
}


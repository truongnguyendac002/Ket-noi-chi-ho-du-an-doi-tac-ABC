package com.msb.insurance.pob.model;

import com.msb.insurance.pob.repository.entity.SercBatchInfo;
import com.msb.insurance.pob.repository.entity.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    @Autowired
    private ModelMapper modelMapper;

    public TransactionDto convertToDto(Transaction transaction) {
        TransactionDto transactionDto = modelMapper.map(transaction, TransactionDto.class);
        transactionDto.setSercBathInfoDto(convertToSercBatchInfoDto(transaction.getSercBathInfo()));
        return transactionDto;
    }

    private SercBatchInfoDto convertToSercBatchInfoDto(SercBatchInfo sercBatchInfo) {
        SercBatchInfoDto sercBatchInfoDto = modelMapper.map(sercBatchInfo, SercBatchInfoDto.class);
        sercBatchInfoDto.setStatus(0);
        return sercBatchInfoDto;
    }
}

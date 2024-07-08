package com.msb.insurance.pob.model.response;

import com.msb.insurance.pob.model.TransactionDto;
import com.msb.insurance.pob.repository.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private String resCode;
    private String resDes;
    private TransactionDto transaction;

    public TransactionResponse(String resCode, String resDes) {
        this.resCode = resCode;
        this.resDes = resDes;
    }
}

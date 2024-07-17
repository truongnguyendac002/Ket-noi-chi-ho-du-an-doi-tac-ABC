package com.msb.insurance.pob.exception;

import com.msb.insurance.pob.common.PobErrorRequest;
import com.msb.insurance.pob.common.PobErrorTransaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PobTransactionException extends RuntimeException{
    private String code;

    private String desc;

    public PobTransactionException(PobErrorTransaction enumError){
        super(enumError.getDesc());
        this.code = enumError.getCode();
        this.desc = enumError.getDesc();
    }
    public PobTransactionException(PobErrorRequest errorRequest){
        super(errorRequest.getRespDesc());
        this.code = errorRequest.getRespCode();
        this.desc = errorRequest.getRespDesc();
    }
}

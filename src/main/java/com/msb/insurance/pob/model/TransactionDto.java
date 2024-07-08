package com.msb.insurance.pob.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {
    private String msgId;
    private String partnerCode;
    private SercBatchInfoDto sercBathInfoDto;
    private String signature;
}

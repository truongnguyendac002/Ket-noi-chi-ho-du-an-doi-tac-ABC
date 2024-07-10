package com.msb.insurance.pob.model.response.put;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PutTransactionResponse {
    private String msgId;
    private String partnerCode;
    private PutBatchResponse sercBatchInfo;
    private String signature;
}

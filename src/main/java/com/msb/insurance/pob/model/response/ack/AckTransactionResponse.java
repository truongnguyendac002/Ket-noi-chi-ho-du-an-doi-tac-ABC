package com.msb.insurance.pob.model.response.ack;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AckTransactionResponse {
    private String msgId;
    private String partnerCode;
    private AckBatchResponse sercBatchInfo;
}

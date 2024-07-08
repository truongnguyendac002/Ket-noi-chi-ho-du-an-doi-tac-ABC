package com.msb.insurance.pob.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.msb.insurance.pob.repository.entity.SercBatchInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TransactionRequest {
    @JsonProperty("msgId")
    private String msgId;

    @JsonProperty("partnerCode")
    private String partnerCode;

    @JsonProperty("sercBatchInfo")
    private SercBatchInfo sercBatchInfo;

    @JsonProperty("signature")
    private String signature;
}

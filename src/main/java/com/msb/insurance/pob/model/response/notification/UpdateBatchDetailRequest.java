package com.msb.insurance.pob.model.response.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateBatchDetailRequest {
    @JsonProperty("msgId")
    private String msgId;
    @JsonProperty("partnerCode")
    private String partnerCode;
    @JsonProperty("sId")
    private String sId;
    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;
//    @JsonProperty("coreRef")
//    private String coreRef;
    @JsonProperty("signature")
    private String signature;
}

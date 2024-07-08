package com.msb.insurance.pob.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SercBatchInfoDto {
    @JsonProperty("batchId")
    private String batchId;
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("requestTime")
    private String requestTime;
    @JsonProperty("totalAmount")
    private double totalAmount;
    @JsonProperty("status")
    private int status;
}

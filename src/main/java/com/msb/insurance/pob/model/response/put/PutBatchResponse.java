package com.msb.insurance.pob.model.response.put;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PutBatchResponse {
    private String batchId;
    private int quantity;
    private String requestTime;
    private double totalAmount;
    private int status;
}

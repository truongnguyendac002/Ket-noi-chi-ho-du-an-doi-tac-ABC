package com.msb.insurance.pob.model.response.ack;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AckBatchResponse {
    private String batchId;
    private int quantity;
    private String requestTime;
    private String status;
    private List<AckBatchDetailResponse> sercBatchDetails;
}

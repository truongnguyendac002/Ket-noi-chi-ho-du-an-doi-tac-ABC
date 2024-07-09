package com.msb.insurance.pob.model.response.ack;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AckBatchDetailResponse {
    private String sId;
    private String status;
}

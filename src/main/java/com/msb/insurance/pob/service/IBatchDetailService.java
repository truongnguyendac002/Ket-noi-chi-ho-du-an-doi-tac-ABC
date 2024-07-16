package com.msb.insurance.pob.service;


import com.msb.insurance.pob.model.response.notification.UpdateBatchDetailRequest;

import java.util.List;

public interface IBatchDetailService {
     List<UpdateBatchDetailRequest> scanPendingBatchDetail();

}

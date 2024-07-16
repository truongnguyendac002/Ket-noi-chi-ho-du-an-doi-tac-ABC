package com.msb.insurance.pob.rest;

import com.msb.insurance.pob.common.PartnerApiAddress;
import com.msb.insurance.pob.model.response.notification.UpdateBatchDetailRequest;
import com.msb.insurance.pob.service.IBatchDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@Slf4j
public class ScheduledUpdateBatchDetail {
    @Autowired
    private IBatchDetailService batchDetailService;

    @Scheduled(fixedRateString = "${batch-detail.update-schedule}" )
    public void updateBatchDetail() {
        System.out.println("updateBatchDetail ...");
        RestTemplate restTemplate = new RestTemplate();
        // Tạo tin nhắn
        List<UpdateBatchDetailRequest> result = batchDetailService.scanPendingBatchDetail();

        // Gửi tin nhắn đến API
        for (UpdateBatchDetailRequest resp : result) {
            log.info("Sending notification: " + resp.toString() + "to" + PartnerApiAddress.PARTNER_API_ADDRESS);
            try {
                restTemplate.postForEntity(PartnerApiAddress.PARTNER_API_ADDRESS, resp, String.class);
                log.info("Sent successfully: " + resp);
            } catch (Exception e) {
                log.error("Error when sending result: " + resp);
            }

        }
    }
}

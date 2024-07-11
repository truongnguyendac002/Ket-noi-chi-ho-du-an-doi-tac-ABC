package com.msb.insurance.pob.rest;

import com.msb.insurance.pob.common.PartnerApiAddress;
import com.msb.insurance.pob.model.response.RespMessage;
import com.msb.insurance.pob.model.rest.ApiForm;
import com.msb.insurance.pob.service.ITransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/api")
public class NotificationController {
    @Autowired
    private ITransactionService transactionService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("apiForm", new ApiForm());
        return "form";
    }


    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submitForm(ApiForm apiForm, Model model) {
        PartnerApiAddress.PARTNER_API_ADDRESS = apiForm.getApiUrl();
        RestTemplate restTemplate = new RestTemplate();
        // Tạo tin nhắn
//        RespMessage result = transactionService.scanPendingTransactions();
        String result = "hello";
        // Gửi tin nhắn đến API
        ResponseEntity<String> response = null;
        try {
            response =restTemplate.postForEntity(PartnerApiAddress.PARTNER_API_ADDRESS, result, String.class) ;
        } catch (Exception e) {
            response =  new ResponseEntity<>("Khong gui duoc den url: " + PartnerApiAddress.PARTNER_API_ADDRESS, null, 500);
        }
        log.info(response.toString());
        return "form";
    }

}

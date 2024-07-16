package com.msb.insurance.pob.rest;

import com.msb.insurance.pob.common.PartnerApiAddress;
import com.msb.insurance.pob.model.response.RespMessage;
import com.msb.insurance.pob.model.rest.ApiForm;
import com.msb.insurance.pob.repository.entity.BatchDetail;
import com.msb.insurance.pob.service.IBatchDetailService;
import com.msb.insurance.pob.service.ITransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Slf4j
@RequestMapping("/api")
public class NotificationController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("apiForm", new ApiForm());
        return "form";
    }


    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submitForm(ApiForm apiForm, Model model) {
        PartnerApiAddress.PARTNER_API_ADDRESS = apiForm.getApiUrl();

        return "form";
    }

}

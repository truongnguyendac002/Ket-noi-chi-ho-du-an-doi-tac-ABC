package com.msb.insurance.pob.rest;

import com.msb.insurance.pob.TestAccount.Account;
import com.msb.insurance.pob.TestAccount.AccountRequest;
import com.msb.insurance.pob.TestAccount.AccountService;
import com.msb.insurance.pob.repository.entity.Users;
import com.msb.insurance.pob.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private AccountService accountService;
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allAccess(){
        return "public content";
    }
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminAccess(){
        return "admin content";
    }
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUser(){
        List<Users> usersList = userService.getAll();
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }
    @RequestMapping(value = "/upload-account", method = RequestMethod.POST)
    public ResponseEntity<?> uploadAccount(@RequestBody AccountRequest request){
        Account account = new Account();
        account.setAccId(request.getAccId());
        account.setLoaiChiHo(request.getLoaiChiHo());
        account.setCAccount(request.getCAccount());
        account.setCName(request.getCName());
        account.setChannel(request.getChannel());
        account.setBankCode(request.getBankCode());
        account.setBankName(request.getBankName());
        accountService.save(account);
        return new ResponseEntity<>("Tao account thanh cong", HttpStatus.OK);
    }
}

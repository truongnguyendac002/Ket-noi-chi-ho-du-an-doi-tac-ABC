package com.msb.insurance.pob.config;

import com.msb.insurance.pob.TestAccount.Account;
import com.msb.insurance.pob.TestAccount.AccountService;
import com.msb.insurance.pob.repository.entity.Users;
import com.msb.insurance.pob.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.AbstractCollection;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    PasswordEncoder encoder;

    public DataInitializer() {

    }

    @Override
    public void run(String... args) {
        initUser();
        initAccount();
    }

    private void initUser(){
        if (userService.existsByUsername("admin")) {
            return;
        }
        Users user = new Users();
        user.setId(1L);
        user.setUsername("admin");
        user.setPassword(encoder.encode("admin"));
        userService.saveAndUpdate(user);
    }

    private void initAccount() {
        Account account1 = new Account("1", "Nội bộ", "888889", "DIEU", "MSB    ", null, null);
        accountService.save(account1);

        Account account2 = new Account("2", "Citad thường", "0129837294", "NGUYEN VAN TEST", "CITAD", "79304001", null);
        accountService.save(account2);

        Account account3 = new Account("3", "Citad Vietcombank", "9876543210", "TEST VCB", "CITAD", "01203003", null);
        accountService.save(account3);

        Account account4 = new Account("4","napas", "88888888", "NGUYEN VAN PASS", "NAPAS","970406", "Ngan hang Dong A" );
        accountService.save(account4);

        Account account5 = new Account("5","napas thẻ", "97040688888888", "NGUYEN VAN PASS", "NAPAS", "970406", "Ngan hang Dong A");
        accountService.save(account5);
    }

}

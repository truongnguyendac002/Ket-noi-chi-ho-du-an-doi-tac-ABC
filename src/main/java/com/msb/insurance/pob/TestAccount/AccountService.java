package com.msb.insurance.pob.TestAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    public List<Account> getAll(){
        return accountRepository.findAll();
    }

    public Account save(Account account){
        return accountRepository.save(account);
    }
}

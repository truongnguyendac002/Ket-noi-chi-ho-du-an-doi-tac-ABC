package com.msb.insurance.pob.service;

import com.msb.insurance.pob.repository.entity.Users;

import java.util.List;

public interface IUserService {
    Users findByUsername(String username);
    Boolean existsByUsername(String username);
    Users saveAndUpdate(Users user);
    List<Users> getAll();
}

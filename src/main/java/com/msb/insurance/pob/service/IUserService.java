package com.msb.insurance.pob.service;

import com.msb.insurance.pob.model.request.SignupRequest;
import com.msb.insurance.pob.repository.entity.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {
    Users findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsById(Long id);
    Users saveAndUpdate(Users user);
    List<Users> getAll();

    ResponseEntity<?> preHandlingSignup(SignupRequest request);
}

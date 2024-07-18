package com.msb.insurance.pob.service.impl;

import com.msb.insurance.pob.model.request.SignupRequest;
import com.msb.insurance.pob.model.response.ResponseMessage;
import com.msb.insurance.pob.repository.entity.Users;
import com.msb.insurance.pob.repository.jpa.UserRepository;
import com.msb.insurance.pob.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public Users saveAndUpdate(Users user) {
        return userRepository.save(user);
    }

    @Override
    public List<Users> getAll() {
        return userRepository.findAll();
    }

    @Override
    public ResponseEntity<?> preHandlingSignup(SignupRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Request không được null"));
        }
        if (existsById(request.getId())) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Id đã tồn tại trong hệ thống"));
        }
        if (existsByUsername(request.getUsername())){
            return ResponseEntity.badRequest().body(new ResponseMessage("Username da ton tai! Vui long thu lai!!"));
        }
        if (!StringUtils.hasText(request.getUsername())){
            return ResponseEntity.badRequest().body(new ResponseMessage("Username không được null"));
        }
        if (!StringUtils.hasText(request.getPassword())){
            return ResponseEntity.badRequest().body(new ResponseMessage("Password không được null"));
        }
        return null;
    }

}

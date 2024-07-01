package com.msb.insurance.pob.repository.jpa;

import com.msb.insurance.pob.repository.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
    Boolean existsByUsername(String username);
}

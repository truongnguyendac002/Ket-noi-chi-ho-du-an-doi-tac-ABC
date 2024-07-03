package com.msb.insurance.pob.repository.jpa;

import com.msb.insurance.pob.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String>{
}

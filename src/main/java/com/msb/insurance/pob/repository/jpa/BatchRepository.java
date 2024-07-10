package com.msb.insurance.pob.repository.jpa;

import com.msb.insurance.pob.repository.entity.SercBatchInfo;
import com.msb.insurance.pob.repository.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BatchRepository extends JpaRepository<SercBatchInfo, String> {

}

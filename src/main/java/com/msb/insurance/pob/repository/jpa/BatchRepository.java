package com.msb.insurance.pob.repository.jpa;

import com.msb.insurance.pob.repository.entity.SercBatchInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchRepository extends JpaRepository<SercBatchInfo, String> {
}

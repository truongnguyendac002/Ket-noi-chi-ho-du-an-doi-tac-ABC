package com.msb.insurance.pob.repository.jpa;

import com.msb.insurance.pob.repository.entity.BatchDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BatchDetailRepository extends JpaRepository<BatchDetail, String> {
    @Query(value = "SELECT * FROM batch_detail WHERE status = '7'", nativeQuery = true)
    Optional<List<BatchDetail>> findPendingBatchDetail();

    @Query(value = "SELECT * FROM batch_detail WHERE s_id = ?1 AND status = '0'", nativeQuery = true)
    Optional<BatchDetail> checkDuplicateSId(String sId);
}

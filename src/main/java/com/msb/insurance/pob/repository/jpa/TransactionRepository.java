package com.msb.insurance.pob.repository.jpa;

import com.msb.insurance.pob.repository.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String>{
    Boolean existsByMsgId(String msgId);

    @Query(value = "SELECT msg_id FROM t_transaction WHERE serc_batch_info_id = :batchId", nativeQuery = true)
    Optional<String> findMsgIdBySercBatchInfoBatchId(@Param("batchId") String batchId);


}

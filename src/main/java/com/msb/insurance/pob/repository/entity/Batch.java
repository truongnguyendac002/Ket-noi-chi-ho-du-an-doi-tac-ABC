package com.msb.insurance.pob.repository.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "batch")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Batch {
    @Id
    private String batchId;
    private int quantity;
    private String requestTime;
    private double totalAmount;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "sercBatchInfo")
    private NewMessage newMessage;
//    @OneToMany(mappedBy = "batch")
//    List<BatchDetails> sercBatchDetails;
}

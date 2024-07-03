package com.msb.insurance.pob.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Entity
@ToString
@Table(name = "serc_batch_info")
public class SercBatchInfo {
    @Id
    @JsonProperty("batchId")
    private String batchId;
    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("requestTime")
    private String requestTime;

    @JsonProperty("totalAmount")
    private double totalAmount;

    @JsonProperty("sercBatchDetails")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BatchDetail> sercBatchDetails;
}

package com.msb.insurance.pob.repository.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "t_transaction")
public class Transaction {
    @Id
    private String msgId;
    private String partnerCode;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "serc_batch_info_id")
    private SercBatchInfo sercBathInfo;

    private String signature;
}

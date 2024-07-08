package com.msb.insurance.pob.repository.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "new_message")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewMessage {
    @Id
    private String msgId;
    private String partnerCode;
    private String signature;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "batchId")
    private Batch sercBatchInfo;
}

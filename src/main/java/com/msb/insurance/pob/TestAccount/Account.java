package com.msb.insurance.pob.TestAccount;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account {
    @Id
    @JsonProperty("accId")
    private String accId;
    @JsonProperty("loaiChiHo")
    private String loaiChiHo;
    @JsonProperty("cAccount")
    private String cAccount;
    @JsonProperty("cName")
    private String cName;
    @JsonProperty("channel")
    private String channel;
    @JsonProperty("bankCode")
    private String bankCode;
    @JsonProperty("bankName")
    private String bankName;
}

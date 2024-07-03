package com.msb.insurance.pob.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@ToString
@Table(name = "batch_detail")
public class BatchDetail {
    @Id
    @JsonProperty("sid")
    private String sId;

    @JsonProperty("cAccount")
    private String cAccount;

    @JsonProperty("cName")
    private String cName;

    @JsonProperty("cAmount")
    private long cAmount;

    @JsonProperty("sAccount")
    private String sAccount;

    @JsonProperty("sName")
    private String sName;

    @JsonProperty("channel")
    private String channel;

    @JsonProperty("bankCode")
    private String bankCode;

    @JsonProperty("bankName")
    private String bankName;

    @JsonProperty("branchCode")
    private String branchCode;

    @JsonProperty("remark")
    private String remark;

    @JsonProperty("bankNostro")
    private String bankNostro;
}

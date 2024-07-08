package com.msb.insurance.pob.TestAccount;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
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

package com.msb.insurance.pob.model.response;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
public class JwtResponse {
    private int respCode;
    private String respDesc;
    private String accessToken;
    private String tokenType;
    private int expiresIn;
    private String Scope;
}

package com.msb.insurance.pob.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum PobErrorRequest {
    Success("0","Success"),
    Fail("P2-01","Fail"),
    Sign_Invalid("P2-76","Sign invalid"),
    Big_Transaction("P2-78","So luong giao dich qua lon");

    private String respCode;
    private String respDesc;
}

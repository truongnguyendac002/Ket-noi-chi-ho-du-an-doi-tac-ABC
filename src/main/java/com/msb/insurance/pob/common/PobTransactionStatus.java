package com.msb.insurance.pob.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public enum PobTransactionStatus {
    Success("0", "Success"),
    Fail("-1", "Fail"),
    Timeout("2", "Timeout"),
    Awaiting("7", "Awaiting");

    private final String code;
    private final String desc;
}

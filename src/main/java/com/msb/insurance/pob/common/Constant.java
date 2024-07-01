package com.msb.insurance.pob.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Constant {
    Success(200,"OK"),
    Bad_Request(400,"Bad Request"),
    Unauthorized(401,"Unauthorized"),
    Not_Found(404,"Not Found"),
    Internal_Server_Error(500,"Internal Server Error"),
    Gateway_Timeout(504,"Gateway Time-out");

    private int respCode;
    private String respDesc;
}

package com.msb.insurance.pob.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class RespMessage {

    private String respCode;
    private String respDesc;
    private Object data;

    public RespMessage(String respCode, String respDesc) {
        this.respCode = respCode;
        this.respDesc = respDesc;
    }
}


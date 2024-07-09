package com.msb.insurance.pob.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public enum PobErrorTransaction {
    Success("0","Success"),
    Fail("-1", "Fail"),
    Timeout("-2", "Timeout to CEP !!!"),
    Khong_du_so_du("5","Khong du so du"),
    SId_not_null("6", "sId is not null !!!"),
    Tk_khong_hd("8", "Tai khoan khong hoat dong"),
    CAccount_invalid("9", "CAccount is invalid !!!"),
    Amount_invalid("10", "Amount is invalid !!!"),
    Invalid_receiver_name("21", "Invalid receiver name !!!"),
    BankNostro_wrong("25", "BankNostro is wrong !!!"),
    SId_duplicated("27", "sId is duplicated !!!"),
    So_tt_trung_nhau("178", "SO TT TRONG LKGD TRUNG NHAU"),
    Date_verification("236", "DATE VERIFICATION ERROR");
    private final String code;
    private final String desc;
}

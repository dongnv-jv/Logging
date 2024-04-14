package com.vn.demo.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LogActionEnum {

    UPDATE("01", "Cập nhật"),
    SAVE("02", "Lưu");

    private final String code;
    private final String name;
}

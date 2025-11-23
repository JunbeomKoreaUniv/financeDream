package com.finance.demo.dto;

import lombok.Getter;

@Getter
public class CheckNicknameResponse {
    private boolean available;

    public CheckNicknameResponse(boolean available) {
        this.available = available;
    }
}
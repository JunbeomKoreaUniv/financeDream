package com.finance.demo.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemberRequest {
    private String email;
    private String password;
    private String confirmPassword;
    private String nickName;
    private List<String> stocks;
}

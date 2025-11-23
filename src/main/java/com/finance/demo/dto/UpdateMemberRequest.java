package com.finance.demo.dto;

import lombok.Getter;
import java.util.List;

@Getter
public class UpdateMemberRequest {
    private List<String> stocks;
}

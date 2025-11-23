package com.finance.demo.dto;

import com.finance.demo.service.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CreateMemberResponse {
    private Long id;
    private String email;
    private String nickName;
    private LocalDateTime createdAt;

    public static CreateMemberResponse fromEntity(Member member) {
        return CreateMemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickName(member.getNickName())
                .createdAt(member.getCreatedAt())
                .build();
    }
}

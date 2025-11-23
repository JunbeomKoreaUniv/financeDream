package com.finance.demo.dto;

import com.finance.demo.service.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class MemberResponse {
    private Long id;
    private String email;
    private String nickname;
    private List<String> stocks;
    private LocalDateTime createdAt;

    public static MemberResponse fromEntity(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickName())
                .stocks(
                        member.getStocks()
                                .stream()
                                .toList()
                )
                .createdAt(LocalDateTime.now())
                .build();
    }
}

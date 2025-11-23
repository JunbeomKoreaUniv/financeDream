package com.finance.demo.service;

import com.finance.demo.dto.*;
import com.finance.demo.repository.MemberRepository;
import com.finance.demo.response.GeneralException;
import com.finance.demo.security.jwt.utils.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public CreateMemberResponse signup(MemberRequest requestDto) {
        validateDuplication(requestDto.getNickName(), requestDto.getEmail());
        if (!requestDto.getPassword().equals(requestDto.getConfirmPassword())) {
            throw new GeneralException(MemberErrorCode.PASSWORD_MISMATCH);
        }

        Member member = Member.builder()
                .email(requestDto.getEmail())
                .nickName(requestDto.getNickName())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .stocks(requestDto.getStocks().stream()
                        .toList())
                .build();

        Member savedMember = memberRepository.save(member);

        return CreateMemberResponse.fromEntity(savedMember);
    }

    @Transactional
    public MemberResponse updateStock(Long memberId, UpdateMemberRequest requestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (requestDto.getStocks() != null) {
            member.updateStocks(requestDto.getStocks().stream()
                    .toList());
        }
        memberRepository.save(member);
        return MemberResponse.fromEntity(member);
    }

    public void validateDuplication(String email, String nickName) {
        validateDuplicateEmail(email);
        validateDuplicateNickName(nickName);
    }
    public CheckEmailResponse validateDuplicateEmail(String email) {
        boolean isExists = memberRepository.existsByEmail(email);
        if (isExists) {
            throw new GeneralException(MemberErrorCode.DUPLICATE_EMAIL);
        }
        return new CheckEmailResponse(true);
    }
    public CheckNicknameResponse validateDuplicateNickName(String nickName) {
        boolean isExists = memberRepository.existsByNickName(nickName);
        if (isExists) {
            throw new GeneralException(MemberErrorCode.DUPLICATE_NICKNAME);
        }
        return new CheckNicknameResponse(true);
    }

    public Long getMemberIdFromToken(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").substring(7);
        return jwtTokenProvider.extractUserId(accessToken).orElseThrow(() -> new GeneralException(MemberErrorCode.INVALID_TOKEN));
    }

    public MemberResponse getMemberInfo(Long memberId) {
        return memberRepository.findById(memberId)
                .map(MemberResponse::fromEntity)
                .orElseThrow(() -> new GeneralException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}

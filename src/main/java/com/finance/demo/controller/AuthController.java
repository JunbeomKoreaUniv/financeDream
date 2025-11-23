package com.finance.demo.controller;

import com.finance.demo.dto.CreateMemberResponse;
import com.finance.demo.dto.MemberRequest;
import com.finance.demo.dto.MemberResponse;
import com.finance.demo.dto.UpdateMemberRequest;
import com.finance.demo.response.ApiResponseForm;
import com.finance.demo.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "자체 회원가입. presigned URL 발급 및 업로드가 선행되어야 함. - JWT 필요 X")
    @PostMapping("api/auth/register")
    public ResponseEntity<ApiResponseForm<CreateMemberResponse>> signup(@RequestBody MemberRequest requestDto) {
        CreateMemberResponse createMemberResponse = authService.signup(requestDto);
        return ResponseEntity.ok(ApiResponseForm.created(createMemberResponse, "회원가입 성공"));
    }

    @Operation(summary = "관심 종목 수정")
    @PutMapping("api/auth")
    public ResponseEntity<ApiResponseForm<MemberResponse>> updateMemberInfo(
            @RequestBody UpdateMemberRequest requestDto,
            HttpServletRequest request) {
        Long memberId = authService.getMemberIdFromToken(request);
        MemberResponse memberResponse = authService.updateStock(memberId, requestDto);
        return ResponseEntity.ok(ApiResponseForm.success(memberResponse, "관심 종목 수정 성공"));
    }

    @Operation(summary = "내 정보 조회")
    @GetMapping("/api/members/me")
    public ResponseEntity<ApiResponseForm<MemberResponse>> getMyInfo(HttpServletRequest request) {
        Long memberId = authService.getMemberIdFromToken(request);
        MemberResponse memberResponse = authService.getMemberInfo(memberId);
        return ResponseEntity.ok(ApiResponseForm.success(memberResponse, "내 정보 조회 성공"));
    }

    @Operation(summary = "로그인 - JWT 필요 X")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일반 로그인 성공", content = @Content(mediaType = "application/json", schema = @Schema(example = "{\n  \"status\": \"success\", \"code\": 200, \"message\": \"로그인 성공\", \"data\": {\"id\": 1, \"email\": \"user@example.com\", \"nickname\": \"user\", \"birth\": \"2000-01-01\", \"gender\": \"MALE\", \"categories\": [\"공부\", \"운동\"], \"createdAt\": \"2023-01-01T00:00:00.000000\"}}"))),
    })
    @PostMapping("/api/auth/login")
    public String login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "로그인 요청 JSON 데이터",
                    required = true,
                    content = @Content(
                            schema = @Schema(type = "object", example = "{\"email\": \"user@example.com\", \"password\": \"1234\"}")
                    )
            )
            @RequestBody Map<String, String> loginRequest) {
        return "로그인 성공"; // 실제 로그인 처리는 Security 필터에서 수행
    }
}
package com.sparta.msa_exam.auth.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.msa_exam.auth.dto.reponse.AuthResponseDto;
import com.sparta.msa_exam.auth.dto.request.LoginRequestDto;
import com.sparta.msa_exam.auth.dto.request.SignUpRequestDto;
import com.sparta.msa_exam.auth.service.CreateAuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

	private final CreateAuthService createAuthService;

	@PostMapping("/sign-up")
	public ResponseEntity<?> singUp(@Valid @RequestBody SignUpRequestDto request) {
		createAuthService.signUp(request);
		return ResponseEntity.ok().body("success");
	}

	@PostMapping(value = "/sign-in", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody LoginRequestDto request) {
		return ResponseEntity.ok(new AuthResponseDto(createAuthService.createAccessToken(request)));
	}
}

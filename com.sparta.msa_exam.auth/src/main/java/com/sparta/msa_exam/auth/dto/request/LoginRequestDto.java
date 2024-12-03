package com.sparta.msa_exam.auth.dto.request;

import lombok.Getter;

@Getter
public class LoginRequestDto {
	private String username;
	private String password;
}

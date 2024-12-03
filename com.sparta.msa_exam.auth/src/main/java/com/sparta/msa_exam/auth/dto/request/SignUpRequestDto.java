package com.sparta.msa_exam.auth.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

	@NotNull
	private String username;

	@NotNull
	private String password;

}

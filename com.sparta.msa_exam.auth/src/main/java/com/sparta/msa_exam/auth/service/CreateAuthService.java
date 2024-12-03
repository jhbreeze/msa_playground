package com.sparta.msa_exam.auth.service;

import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sparta.msa_exam.auth.dto.request.LoginRequestDto;
import com.sparta.msa_exam.auth.dto.request.SignUpRequestDto;
import com.sparta.msa_exam.auth.entity.User;
import com.sparta.msa_exam.auth.repository.AuthRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
public class CreateAuthService {

	@Value("${spring.application.name}")
	private String issuer;

	@Value("${service.jwt.access-expiration}")
	private Long accessExpiration;

	private final AuthRepository authRepository;
	private final PasswordEncoder passwordEncoder;
	private final SecretKey secretKey;

	public CreateAuthService(@Value("${service.jwt.secret-key}") String secretKey,
		AuthRepository authRepository, PasswordEncoder passwordEncoder) {
		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
		this.authRepository = authRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public void signUp(SignUpRequestDto request) {
		String username = request.getUsername();
		String password = passwordEncoder.encode(request.getPassword());

		Optional<User> checkUsername = authRepository.findByUsername(username);
		if (checkUsername.isPresent()) {
			throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
		}

		User user = new User(username, password);
		authRepository.save(user);
	}

	public String createAccessToken(LoginRequestDto request) {
		return Jwts.builder()
			.claim("username", request.getUsername())
			.issuer(issuer)
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + accessExpiration))
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();

	}
}

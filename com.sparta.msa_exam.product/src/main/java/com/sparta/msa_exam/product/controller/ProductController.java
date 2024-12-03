package com.sparta.msa_exam.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.msa_exam.product.dto.request.CreateProductRequestDto;
import com.sparta.msa_exam.product.dto.response.GetProductsResponseDto;
import com.sparta.msa_exam.product.service.CreateProductService;
import com.sparta.msa_exam.product.service.GetProductsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

	@Value("${server.port}")
	private String serverPort;

	private final CreateProductService createProductService;
	private final GetProductsService getProductsService;

	@PostMapping
	public ResponseEntity<Long> createProduct(@Valid @RequestBody CreateProductRequestDto request) {
		Long productId = createProductService.createProduct(request);
		return ResponseEntity.ok(productId);
	}

	@GetMapping
	public List<GetProductsResponseDto> getProducts() {
		log.info(" fuck From port : " + serverPort );
		System.out.println("지금 포트 누구냐 " + serverPort);
		return getProductsService.getProducts();
	}

	@GetMapping("/exists")
	public List<Long> validateProducts(@RequestParam List<Long> productIds) {
		return getProductsService.validateProducts(productIds);
	}
}

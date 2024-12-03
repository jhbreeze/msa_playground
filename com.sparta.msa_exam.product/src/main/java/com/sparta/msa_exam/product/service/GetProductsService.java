package com.sparta.msa_exam.product.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.sparta.msa_exam.product.dto.response.GetProductsResponseDto;
import com.sparta.msa_exam.product.entity.Product;
import com.sparta.msa_exam.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetProductsService {

	private final ProductRepository productRepository;

	@Cacheable(cacheNames = "productsCache", key = "methodName")
	public List<GetProductsResponseDto> getProducts() {
		List<Product> products = productRepository.findAll();
		return products.stream().map(GetProductsResponseDto::fromEntity).toList();
	}

	public List<Long> validateProducts(@RequestParam List<Long> productIds) {
		return productRepository.findAllByIdIn(productIds)
			.stream()
			.map(Product::getId)
			.toList();
	}
}

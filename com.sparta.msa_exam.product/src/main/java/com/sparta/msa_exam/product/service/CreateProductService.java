package com.sparta.msa_exam.product.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.sparta.msa_exam.product.dto.request.CreateProductRequestDto;
import com.sparta.msa_exam.product.entity.Product;
import com.sparta.msa_exam.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateProductService {

	private final ProductRepository productRepository;

	@CacheEvict(cacheNames = "productsCache", allEntries = true)
	public Long createProduct(CreateProductRequestDto request) {

		Product product = Product.builder()
			.name(request.getName())
			.description(request.getDescription())
			.supplyPrice(request.getSupplyPrice())
			.salesPrice(request.getSalesPrice())
			.build();

		Product savedProduct = productRepository.save(product);

		return savedProduct.getId();
	}

}

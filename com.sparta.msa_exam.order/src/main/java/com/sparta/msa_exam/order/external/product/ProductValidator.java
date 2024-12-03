package com.sparta.msa_exam.order.external.product;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductValidator {

	private final ProductClient productClient;

	@CircuitBreaker(name = "productService", fallbackMethod = "FallbackCallProductService")
	public List<Long> callProductService(List<Long> productIds) {
		if (productIds.contains(7777L)) {
			throw new RuntimeException("Failure for product ID 7777");
		}

		return productClient.validateProducts(productIds);
	}

	public List<Long> FallbackCallProductService(List<Long> productIds, Throwable e) {
		log.error("#### Fallback triggered for productIds: {} due to: {}", productIds, e.getMessage());
		return Collections.emptyList();
	}
}

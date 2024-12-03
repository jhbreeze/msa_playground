package com.sparta.msa_exam.order.external.product;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product")
public interface ProductClient {

	@GetMapping("/products/{id}")
	String getProduct(@PathVariable("id") Long id);

	@GetMapping("/products/exists")
	List<Long> validateProducts(@RequestParam("productIds") List<Long> productIds);

}

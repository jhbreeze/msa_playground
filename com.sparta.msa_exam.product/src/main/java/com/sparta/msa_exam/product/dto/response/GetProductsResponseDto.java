package com.sparta.msa_exam.product.dto.response;

import java.io.Serializable;

import com.sparta.msa_exam.product.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GetProductsResponseDto implements Serializable {

	private String name;

	private String description;

	private Integer supplyPrice;

	private Integer salesPrice;

	public static GetProductsResponseDto fromEntity(Product product) {
		return GetProductsResponseDto.builder()
			.name(product.getName())
			.description(product.getDescription())
			.salesPrice(product.getSupplyPrice())
			.salesPrice(product.getSalesPrice())
			.build();
	}
}

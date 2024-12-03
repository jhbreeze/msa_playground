package com.sparta.msa_exam.product.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequestDto {

	@NotNull
	private String name;

	private String description;

	@NotNull
	private Integer supplyPrice;

	private Integer salesPrice;
}

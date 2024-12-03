package com.sparta.msa_exam.order.dto.request;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateOrderRequestDto {

	private List<OrderLineItemRequest> orderLineItems;

	@Getter
	public static class OrderLineItemRequest{

		@NotNull
		private Long productId;

		@NotNull
		@Min(1)
		private int quantity;

	}

}


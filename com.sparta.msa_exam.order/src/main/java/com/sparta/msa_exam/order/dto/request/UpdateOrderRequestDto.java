package com.sparta.msa_exam.order.dto.request;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateOrderRequestDto {

	private Long orderId;
	private List<OrderLineItemRequest> orderLineItems;

	@Getter
	public static class OrderLineItemRequest{

		@NotNull
		private Long productId;

		@NotNull(message = "수량도 입력해주세요")
		@Min(1)
		private int quantity;

	}

}
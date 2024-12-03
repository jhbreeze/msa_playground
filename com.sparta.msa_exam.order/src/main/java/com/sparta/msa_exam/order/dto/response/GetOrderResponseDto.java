package com.sparta.msa_exam.order.dto.response;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetOrderResponseDto implements Serializable {

	private Long orderId;

	private List<Long> productIds;

}
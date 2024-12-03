package com.sparta.msa_exam.order.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.msa_exam.order.dto.request.CreateOrderRequestDto;
import com.sparta.msa_exam.order.dto.request.UpdateOrderRequestDto;
import com.sparta.msa_exam.order.dto.response.GetOrderResponseDto;
import com.sparta.msa_exam.order.service.CreateOrderService;
import com.sparta.msa_exam.order.service.GetOrderService;
import com.sparta.msa_exam.order.service.UpdateOrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

	private final CreateOrderService createOrderService;
	private final UpdateOrderService updateOrderService;
	private final GetOrderService getOrderService;

	@PostMapping
	public ResponseEntity<?> createOrder(@Valid @RequestBody CreateOrderRequestDto request,
		@RequestParam(required = false) boolean fail) {

		if (fail) { // fallback 함수 구현 요구사항을 위해 임시로 넣은 코드
			List<Long> productIds = createOrderService.failOrder();
			if(productIds.isEmpty()) return ResponseEntity.badRequest().body("잠시 후에 주문 추가를 요청해주세요");
		}

		Long orderId = createOrderService.createOrder(request);
		return ResponseEntity.ok(orderId);
	}

	@PutMapping
	public ResponseEntity<Long> updateOrder(@Valid @RequestBody UpdateOrderRequestDto request) {
		Long orderId = updateOrderService.updateOrder(request);
		return ResponseEntity.ok(orderId);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<GetOrderResponseDto> getOrder(@PathVariable Long orderId) {
		GetOrderResponseDto responseDto = getOrderService.getOrder(orderId);
		return ResponseEntity.ok(responseDto);
	}

	@PostMapping("?fail")
	public ResponseEntity<String> failOrder() {
		createOrderService.failOrder();
		return ResponseEntity.badRequest().body("잠시 후에 주문 추가를 요청해주세요");
	}
}

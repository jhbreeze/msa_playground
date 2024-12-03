package com.sparta.msa_exam.order.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.msa_exam.order.dto.response.GetOrderResponseDto;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderLineItem;
import com.sparta.msa_exam.order.repository.OrderRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetOrderService {

	private final OrderRepository orderRepository;

	@Cacheable(cacheNames = "readOrderCache", key = "args[0]") // cache aside 방식 // 반환값을 캐시하므로, 직렬화 필요
	@Transactional(readOnly = true)
	public GetOrderResponseDto getOrder(Long orderId) {
		Order order = findOrder(orderId);
		List<Long> orderLineItemIds = findOrderLineItemIds(order);

		return GetOrderResponseDto.builder()
			.orderId(order.getId())
			.productIds(orderLineItemIds)
			.build();
	}

	private Order findOrder(Long orderId) {
		return orderRepository.findById(orderId)
			.orElseThrow(() -> new EntityNotFoundException("Order not found"));
	}

	private static List<Long> findOrderLineItemIds(Order order) {
		return order.getOrderLineItems()
			.stream()
			.map(OrderLineItem::getId)
			.toList();
	}
}
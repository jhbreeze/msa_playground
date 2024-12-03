package com.sparta.msa_exam.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.msa_exam.order.dto.request.UpdateOrderRequestDto;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderLineItem;
import com.sparta.msa_exam.order.external.product.ProductClient;
import com.sparta.msa_exam.order.repository.OrderRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateOrderService {

	private final ProductClient productClient;
	private final OrderRepository orderRepository;

	@Transactional
	public Long updateOrder(UpdateOrderRequestDto request) {

		validateProducts(request.getOrderLineItems());

		Order order = getOrder(request);

		request.getOrderLineItems().forEach(lineItem -> {
			OrderLineItem orderLineItem = OrderLineItem.builder()
				.productId(lineItem.getProductId())
				.quantity(lineItem.getQuantity())
				.build();

			order.addOrUpdateOrderLineItem(orderLineItem);
		});

		return order.getId();
	}

	private Order getOrder(UpdateOrderRequestDto request) {
		return orderRepository.findById(request.getOrderId())
			.orElseThrow(() -> new EntityNotFoundException("Order not found"));
	}

	private void validateProducts(List<UpdateOrderRequestDto.OrderLineItemRequest> orderLineItems) {

		List<Long> productIds = orderLineItems.stream()
			.map(UpdateOrderRequestDto.OrderLineItemRequest::getProductId)
			.toList();

		try {
			List<Long> existingProductIds = productClient.validateProducts(productIds);

			List<Long> notFoundProductIds = productIds.stream()
				.filter(id -> !existingProductIds.contains(id))
				.toList();

			if (!notFoundProductIds.isEmpty()) {
				throw new IllegalArgumentException("상품이 존재하지 않습니다. ProductIds: " + notFoundProductIds);
			}

		} catch (Exception e) {
			throw new RuntimeException("상품 서비스 호출 중 오류가 발생했습니다.", e);
		}
	}


}

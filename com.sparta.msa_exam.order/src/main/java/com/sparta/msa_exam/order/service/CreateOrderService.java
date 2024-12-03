package com.sparta.msa_exam.order.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sparta.msa_exam.order.dto.request.CreateOrderRequestDto;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderLineItem;
import com.sparta.msa_exam.order.external.product.ProductValidator;
import com.sparta.msa_exam.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "createOrder")
@Service
@RequiredArgsConstructor
public class CreateOrderService {

	private final OrderRepository orderRepository;
	private final ProductValidator productValidator;

	public Long createOrder(CreateOrderRequestDto request) {
		validateProducts(request.getOrderLineItems());
		Order savedOrder = saveProcess(request, orderRepository);
		return savedOrder.getId();
	}

	public List<Long> failOrder() {
		return productValidator.callProductService(List.of(7777L));
	}

	private void validateProducts(List<CreateOrderRequestDto.OrderLineItemRequest> orderLineItems) {

		List<Long> productIds = orderLineItems.stream()
			.map(CreateOrderRequestDto.OrderLineItemRequest::getProductId)
			.toList();

		List<Long> existingProductIds = productValidator.callProductService(productIds);

		List<Long> notFoundProductIds = productIds.stream()
			.filter(id -> !existingProductIds.contains(id))
			.toList();

		if (!notFoundProductIds.isEmpty()) {
			throw new IllegalArgumentException("상품이 존재하지 않습니다. ProductIds: " + notFoundProductIds);
		}
	}

	private static Order saveProcess(CreateOrderRequestDto request, OrderRepository orderRepository) {
		Order order = new Order();

		request.getOrderLineItems().forEach(lineItem -> {
			OrderLineItem orderLineItem = OrderLineItem.builder()
				.productId(lineItem.getProductId())
				.quantity(lineItem.getQuantity())
				.build();

			order.addOrUpdateOrderLineItem(orderLineItem);
		});

		return orderRepository.save(order);
	}

}
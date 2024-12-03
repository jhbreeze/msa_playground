package com.sparta.msa_exam.order.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderLineItem> orderLineItems = new ArrayList<>();


	public void addOrUpdateOrderLineItem(OrderLineItem orderLineItem) {

		Optional<OrderLineItem> existingItem = this.orderLineItems.stream()
			.filter(item -> item.getProductId().equals(orderLineItem.getProductId()))
			.findFirst();

		if (existingItem.isPresent()) {
			existingItem.get().addQuantity(orderLineItem.getQuantity());
		} else {
			this.orderLineItems.add(orderLineItem);
			orderLineItem.setOrder(this);
		}
	}

}


package com.sparta.msa_exam.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_line_item")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class OrderLineItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	private Long productId;

	private int quantity;

	protected void setOrder(Order order) {
		this.order = order;
	}

	public void addQuantity(int quantity) {
		this.quantity += quantity;
	}
}

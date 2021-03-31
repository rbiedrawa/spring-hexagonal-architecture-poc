package com.rbiedrawa.hexagonal.app.postgres.orders;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.rbiedrawa.hexagonal.app.business.orders.models.Order;
import com.rbiedrawa.hexagonal.app.business.orders.models.OrderStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Builder(toBuilder = true)
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class OrderEntity {

	@Id
	// @Type(type = "pg-uuid")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	@Column(unique = true, nullable = false, columnDefinition = "uuid")
	@Builder.Default
	private UUID id = UUID.randomUUID();

	private String customerFullName;
	private String orderItemName;
	private BigDecimal totalPrice;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	public static OrderEntity of(Order order) {
		return OrderEntity.builder()
						  .id(order.getId())
						  .customerFullName(order.getCustomerFullName())
						  .orderItemName(order.getProductName())
						  .totalPrice(order.getTotalPrice())
						  .status(order.getStatus())
						  .build();
	}

	public Order toOrder() {
		return Order.builder()
					.id(this.id)
					.customerFullName(this.customerFullName)
					.productName(this.orderItemName)
					.totalPrice(this.totalPrice)
					.status(this.status)
					.build();
	}
}

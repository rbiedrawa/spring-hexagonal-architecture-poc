package com.rbiedrawa.hexagonal.app.spi.postgres.orders;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.rbiedrawa.hexagonal.app.business.orders.models.Order;
import com.rbiedrawa.hexagonal.app.business.orders.models.OrderStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String customerFullName;
	private String productName;
	private BigDecimal totalPrice;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	public static OrderEntity of(Order order) {
		return OrderEntity.builder()
						  .id(order.getId())
						  .customerFullName(order.getCustomerFullName())
						  .productName(order.getProductName())
						  .totalPrice(order.getTotalPrice())
						  .status(order.getStatus())
						  .build();
	}

	public Order toOrder() {
		return Order.builder()
					.id(this.id)
					.customerFullName(this.customerFullName)
					.productName(this.productName)
					.totalPrice(this.totalPrice)
					.status(this.status)
					.build();
	}

	// https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof OrderEntity))
			return false;

		OrderEntity other = (OrderEntity) o;

		return id != null &&
			   id.equals(other.getId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}

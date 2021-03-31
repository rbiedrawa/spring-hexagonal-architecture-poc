package com.rbiedrawa.hexagonal.app.api.graphql.orders;


import java.util.List;
import java.util.stream.Collectors;

import com.rbiedrawa.hexagonal.app.api.graphql.gen.DgsConstants;
import com.rbiedrawa.hexagonal.app.api.graphql.gen.types.CreateOrder;
import com.rbiedrawa.hexagonal.app.api.graphql.gen.types.OrderDetails;
import com.rbiedrawa.hexagonal.app.business.common.UuidGenerator;
import com.rbiedrawa.hexagonal.app.business.common.exceptions.NotFoundException;
import com.rbiedrawa.hexagonal.app.business.orders.OrderCommandService;
import com.rbiedrawa.hexagonal.app.business.orders.OrderQueryService;
import com.rbiedrawa.hexagonal.app.business.orders.models.NewOrder;
import com.rbiedrawa.hexagonal.app.business.orders.models.Order;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class OrderGraph {
	private final OrderCommandService orderCommandService;
	private final OrderQueryService orderQueryService;

	@DgsData(parentType = DgsConstants.QUERY.TYPE_NAME, field = DgsConstants.QUERY.Orders)
	public List<OrderDetails> orders() {
		return orderQueryService.findAll()
								.stream()
								.map(OrderAssembler::to)
								.collect(Collectors.toList());
	}

	@DgsData(parentType = DgsConstants.QUERY.TYPE_NAME, field = DgsConstants.QUERY.Order)
	public OrderDetails findById(@InputArgument("orderId") String orderId) {
		return orderQueryService.findById(UuidGenerator.from(orderId))
								.map(OrderAssembler::to)
								.orElseThrow(() -> NotFoundException.entityNotFound("Order", orderId));
	}

	@DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.CreateOrder)
	public OrderDetails create(@InputArgument("createOrder") CreateOrder createOrder) {
		Order order = orderCommandService.createOrder(NewOrder.of(createOrder.getCustomerId(), createOrder.getProduct()));
		return OrderAssembler.to(order);
	}

}

package com.rbiedrawa.hexagonal.app.api.rest.orders;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.rbiedrawa.hexagonal.app.api.rest.config.GlobalExceptionHandler;
import com.rbiedrawa.hexagonal.app.business.common.UuidGenerator;
import com.rbiedrawa.hexagonal.app.business.orders.OrderCommandService;
import com.rbiedrawa.hexagonal.app.business.orders.OrderQueryService;
import com.rbiedrawa.hexagonal.app.business.orders.models.Order;
import com.rbiedrawa.hexagonal.app.business.orders.models.OrderStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

@WebMvcTest(controllers = {OrderController.class, GlobalExceptionHandler.class})
class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderCommandService orderCommandService;

	@MockBean
	private OrderQueryService orderQueryService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void should_create_new_order() throws Exception {
		// Given
		var createOrderRequest = new CreateOrderRequest(UuidGenerator.generateAsString(), "New Product");

		var order = validOrder(UUID.randomUUID());
		when(orderCommandService.createOrder(any())).thenReturn(order);

		// When
		var result = mockMvc.perform(post("/orders")
										 .contentType(MediaType.APPLICATION_JSON)
										 .content(asJson(createOrderRequest)));

		// Then
		result.andExpect(status().is2xxSuccessful())
			  .andExpect(jsonPath("orderId").value(order.idAsString()))
			  .andExpect(jsonPath("status").value(order.getStatus().toString()));
	}

	@Test
	void should_create_random_order() throws Exception {
		// Given
		var order = validOrder(UUID.randomUUID());

		when(orderCommandService.createOrder(any())).thenReturn(order);

		// When
		var result = mockMvc.perform(post("/orders/random"));

		// Then
		result.andExpect(status().is2xxSuccessful())
			  .andExpect(jsonPath("orderId").value(order.idAsString()))
			  .andExpect(jsonPath("status").value(order.getStatus().toString()));
	}

	@Test
	void should_return_all_orders() throws Exception {
		// Given
		UUID orderId = UUID.randomUUID();
		Order order = validOrder(orderId);

		when(orderQueryService.findAll()).thenReturn(List.of(order));

		// When
		var result = mockMvc.perform(get("/orders"));

		// Then
		result.andExpect(status().is2xxSuccessful())
			  .andExpect(jsonPath("$", hasSize(1)))
			  .andExpect(jsonPath("$[0].id", is(order.getId().toString())))
			  .andExpect(jsonPath("$[0].productName", is(order.getProductName())));
	}

	@Test
	void should_return_order() throws Exception {
		// Given
		var order = validOrder(UUID.randomUUID());

		when(orderQueryService.findById(order.getId())).thenReturn(Optional.of(order));

		// When
		var result = mockMvc.perform(get("/orders/{orderId}", order.idAsString()));

		// Then
		result.andExpect(status().is2xxSuccessful())
			  .andExpect(jsonPath("id").value(order.idAsString()))
			  .andExpect(jsonPath("totalPrice").value(order.getTotalPrice().toString()))
			  .andExpect(jsonPath("productName").value(order.getProductName()))
			  .andExpect(jsonPath("status").value(order.getStatus().toString()))
			  .andExpect(jsonPath("customerFullName").value(order.getCustomerFullName()));
	}

	private Order validOrder(UUID orderId) {
		return Order.builder().id(orderId)
					.customerFullName("Test User")
					.productName("Test product")
					.status(OrderStatus.APPROVED)
					.totalPrice(BigDecimal.TEN)
					.build();
	}

	private String asJson(CreateOrderRequest new_product) {
		try {
			return objectMapper.writeValueAsString(new_product);
		} catch (JsonProcessingException ex) {
			throw new RuntimeException(ex);
		}
	}

}

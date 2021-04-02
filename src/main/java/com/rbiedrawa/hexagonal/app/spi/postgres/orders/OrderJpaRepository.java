package com.rbiedrawa.hexagonal.app.spi.postgres.orders;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {
}

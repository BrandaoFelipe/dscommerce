package com_brandao.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com_brandao.dscommerce.entities.OrderItem;
import com_brandao.dscommerce.entities.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK>{

}

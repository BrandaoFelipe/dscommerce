package com_brandao.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com_brandao.dscommerce.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}

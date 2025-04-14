package com_brandao.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com_brandao.dscommerce.dtos.OrderDTO;
import com_brandao.dscommerce.entities.Order;
import com_brandao.dscommerce.repositories.OrderRepository;
import com_brandao.dscommerce.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id){
        Order order = repository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Resource not found"));

            return new OrderDTO(order);
    }
}

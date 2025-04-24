package com_brandao.dscommerce.services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com_brandao.dscommerce.dtos.OrderDTO;
import com_brandao.dscommerce.dtos.OrderItemDTO;
import com_brandao.dscommerce.entities.Order;
import com_brandao.dscommerce.entities.OrderItem;
import com_brandao.dscommerce.entities.OrderStatus;
import com_brandao.dscommerce.entities.Product;
import com_brandao.dscommerce.entities.User;
import com_brandao.dscommerce.repositories.OrderItemRepository;
import com_brandao.dscommerce.repositories.OrderRepository;
import com_brandao.dscommerce.repositories.ProductRepository;
import com_brandao.dscommerce.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found"));       

       authService.validateSelfOrAdmin(order.getClient().getId());

        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto) {

        Order order = new Order();

        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        User user = userService.authenticated();
        order.setClient(user);

        for (OrderItemDTO itemDto : dto.getItems()) {
            Product product = productRepository.getReferenceById(itemDto.getProductId());
            OrderItem orderItem = new OrderItem(order, product, itemDto.getQuantity(), product.getPrice());
            order.getOrderItems().add(orderItem);
        }

        repository.save(order);
        orderItemRepository.saveAll(order.getOrderItems());

        return new OrderDTO(order);
    }

}

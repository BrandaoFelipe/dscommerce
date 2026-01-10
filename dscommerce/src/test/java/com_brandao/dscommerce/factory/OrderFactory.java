package com_brandao.dscommerce.factory;

import com_brandao.dscommerce.dtos.*;
import com_brandao.dscommerce.entities.*;

import java.time.Instant;
import java.util.stream.Collectors;

public class OrderFactory {

    public static Order createOrder(){

        User client = UserFactory.createUser();
        Product prod1 = ProductFactory.createProduct();
        Product prod2 = ProductFactory.createProduct2();
        Instant time = Instant.now();
        OrderStatus status = OrderStatus.WAITING_PAYMENT;

        Order order = new Order(1L, time, status, client, null);

        Payment payment = new Payment(1L, time, order);

        order.getOrderItems().add(new OrderItem(order, prod1, 2, prod1.getPrice()));
        order.getOrderItems().add(new OrderItem(order, prod2, 3, prod2.getPrice()));
        order.setPayment(payment);

        return order;
    }

    public static OrderDTO createOrderDTO(){

        PaymentDTO pDto = new PaymentDTO(createOrder().getPayment().getId(), createOrder().getPayment().getMoment());
        ClientDTO cDto = new ClientDTO(createOrder().getClient());

        OrderDTO dto = new OrderDTO(createOrder().getId(), createOrder().getMoment(), createOrder().getStatus(),
                cDto, pDto);

        ProductDTO prodDTO = new ProductDTO(ProductFactory.createProduct());
        ProductDTO prodDTO2 = new ProductDTO(ProductFactory.createProduct2());

        dto.getItems().add(new OrderItemDTO(prodDTO.getId(), prodDTO.getName(), prodDTO.getPrice(), 2, prodDTO.getImgUrl()));
        dto.getItems().add(new OrderItemDTO(prodDTO2.getId(), prodDTO2.getName(), prodDTO2.getPrice(), 3, prodDTO2.getImgUrl()));

        return dto;
    }
}

package com_brandao.dscommerce.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com_brandao.dscommerce.dtos.OrderDTO;
import com_brandao.dscommerce.services.OrderService;


@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService service;
  
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id){
        
        return ResponseEntity.ok(service.findById(id));
    } 

}
package com_brandao.dscommerce.services;

import com_brandao.dscommerce.dtos.OrderDTO;
import com_brandao.dscommerce.dtos.OrderItemDTO;
import com_brandao.dscommerce.entities.Order;
import com_brandao.dscommerce.entities.Product;
import com_brandao.dscommerce.entities.User;
import com_brandao.dscommerce.factory.OrderFactory;
import com_brandao.dscommerce.factory.ProductFactory;
import com_brandao.dscommerce.factory.UserFactory;
import com_brandao.dscommerce.repositories.OrderItemRepository;
import com_brandao.dscommerce.repositories.OrderRepository;
import com_brandao.dscommerce.repositories.ProductRepository;
import com_brandao.dscommerce.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class OrderServiceTests {

    @InjectMocks
    private OrderService service;
    @Mock
    private OrderRepository repository;
    @Mock
    private ProductRepository prodRepository;
    @Mock
    private OrderItemRepository orderRepository;
    @Mock
    private AuthService authService;
    @Mock
    private UserService userService;

    private Order order;
    private User user;
    private Product product1;
    private Product product2;
    private OrderDTO orderDto;
    private Long validOrderId;
    private Long invalidOrderId;
    private Long validCliendId;
    private Long prod1;
    private Long prod2;

    @BeforeEach
    void setUp() throws Exception {

        order = OrderFactory.createOrder();
        orderDto = OrderFactory.createOrderDTO();
        user = UserFactory.createUser();
        product1 = ProductFactory.createProduct();
        product2 = ProductFactory.createProduct2();
        validOrderId = 1L;
        invalidOrderId = 999L;
        validCliendId = 1L;
        prod1 = 1L;
        prod2 = 2L;

        Mockito.when(userService.authenticated()).thenReturn(user);
        Mockito.doNothing().when(authService).validateSelfOrAdmin(validCliendId);
        Mockito.when(repository.findById(validOrderId)).thenReturn(Optional.of(order));
        Mockito.when(repository.save(any())).thenReturn(order);
        Mockito.when(orderRepository.saveAll(any())).thenReturn(new ArrayList<>());
        Mockito.when(prodRepository.getReferenceById(prod1)).thenReturn(product1);
        Mockito.when(prodRepository.getReferenceById(prod2)).thenReturn(product2);
    }

    @Test
    public void findByIdShouldReturnOrderWhenValidId() {

        OrderDTO dto = service.findById(validOrderId);

        Assertions.assertNotNull(dto);
        System.out.println("Assertions.assertNotNull(dto) - PASS");

        Assertions.assertEquals(1L, dto.getId());
        System.out.println("Assertions.assertEquals(1L, dto.getId()) - PASS");
    }

    @Test
    public void findByIfShouldThrowResourceNotFoundExceptionWHenInvalidId() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(invalidOrderId);
        });
        System.out.println("Threw ResourceNotFoundException");
    }

    @Test
    public void insertShouldInsertItemsWhenOrderIsOk(){

        OrderDTO result = service.insert(orderDto);
        String name = result.getItems().stream().map(OrderItemDTO::getName).findFirst().get();

        Assertions.assertNotNull(result);
        System.out.println("Assertions.assertNotNull(result)");
        Assertions.assertEquals(1L, result.getId());
        System.out.println("Assertions.assertEquals(1L, result.getId())");
        Assertions.assertEquals("teste", name);
        System.out.println("Assertions.assertEquals(teste, name)");
    }
}

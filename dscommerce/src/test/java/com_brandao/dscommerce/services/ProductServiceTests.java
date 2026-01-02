package com_brandao.dscommerce.services;

import com_brandao.dscommerce.dtos.ProductDTO;
import com_brandao.dscommerce.entities.Product;
import com_brandao.dscommerce.factory.ProductFactory;
import com_brandao.dscommerce.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    private long existingProductId, nonExistingProductId;
    private Product product;

    @BeforeEach
    void setUp() throws Exception {
        existingProductId = 1L;
        nonExistingProductId = 2L;
        product = ProductFactory.createProduct();

        Mockito.when(repository.findById(existingProductId)).thenReturn(Optional.of(product));
    }

    @Test
    public void findByIdShouldReturnProductDTOWhenIdExists(){
        ProductDTO dto = service.findById(existingProductId);
        System.out.println("called service.findById");

        Assertions.assertNotNull(dto);
        System.out.println("dto not null");

        Assertions.assertEquals(dto.getId(), existingProductId);
        System.out.println("dto Id: " + dto.getId() + " existing id: " + existingProductId);

        Assertions.assertEquals(dto.getName(), product.getName());
        System.out.println("dto name: " + dto.getName() + " existing name: " + product.getName());

    }

}

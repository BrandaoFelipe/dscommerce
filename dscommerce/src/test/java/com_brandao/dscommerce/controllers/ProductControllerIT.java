package com_brandao.dscommerce.controllers;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com_brandao.dscommerce.dtos.ProductDTO;
import com_brandao.dscommerce.factory.ProductFactory;
import com_brandao.dscommerce.resources.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objMap;

    @Autowired
    private TokenUtil tokenUtil;

    private long existingId;
    private long nonExistingId;
    private long productTotalCount;
    private String clientUsername, adminUsername, password, bearerToken, nameForSearch;


    @BeforeEach
    void setup() throws Exception {

        existingId = 1L;
        nonExistingId = 999L;
        productTotalCount = 22L;
        adminUsername = "alex@gmail.com";
        clientUsername = "maria@gmail.com";
        password = "123456";
        nameForSearch = "Macbook";

        bearerToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, password);
    }

    @Test
    public void findProductsByNameShouldReturnProductWhenNameIsValid() throws Exception {
        ResultActions result = mockMvc.perform(get("/products")
                .param("name", nameForSearch)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").value(3L));
        result.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
        result.andExpect(jsonPath("$.content[0].price").value(1250.0));
    }

    @Test
    public void insertProductShouldInsertProductWhenValidDataAndLoggedWithAdmin() throws Exception {

        ProductDTO productDto = ProductFactory.createProductDto();
        String jsonBody = objMap.writeValueAsString(productDto);

        ResultActions result = mockMvc.perform(post("/products")
                .header("Authorization", "Bearer " + bearerToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
    }

    @Test
    public void insertProductShouldReturnUnprocessableEntityWhenInvalidNameAndLoggedWithAdmin() throws Exception {

        ProductDTO productDto = ProductFactory.createProductDto().toBuilder().name(" ").build();

        String jsonBody = objMap.writeValueAsString(productDto);

        ResultActions result = mockMvc.perform(post("/products")
                .header("Authorization", "Bearer " + bearerToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertProductShouldReturnUnprocessableEntityWhenInvalidDescriptionAndLoggedWithAdmin() throws Exception {

        ProductDTO productDto = ProductFactory.createProductDto().toBuilder().description(" ").build();

        String jsonBody = objMap.writeValueAsString(productDto);

        ResultActions result = mockMvc.perform(post("/products")
                .header("Authorization", "Bearer " + bearerToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertProductShouldReturnUnprocessableEntityWhenInvalidPriceAndLoggedWithAdmin() throws Exception {

        ProductDTO productDto = ProductFactory.createProductDto().toBuilder().price(-10.0).build();

        String jsonBody = objMap.writeValueAsString(productDto);

        ResultActions result = mockMvc.perform(post("/products")
                .header("Authorization", "Bearer " + bearerToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertProductShouldReturnUnprocessableEntityWhenNoCategoryAddedAndLoggedWithAdmin() throws Exception {

        ProductDTO productDto = ProductFactory.createProductDto().toBuilder().categories(List.of()).build();

        String jsonBody = objMap.writeValueAsString(productDto);

        ResultActions result = mockMvc.perform(post("/products")
                .header("Authorization", "Bearer " + bearerToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertProductShouldReturnForbiddenWhenValidDataAndLoggedWithClient() throws Exception {

        bearerToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, password);

        ProductDTO productDto = ProductFactory.createProductDto();

        String jsonBody = objMap.writeValueAsString(productDto);

        ResultActions result = mockMvc.perform(post("/products")
                .header("Authorization", "Bearer " + bearerToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isForbidden());
    }

    @Test
    public void insertProductShouldReturnForbiddenWhenValidDataAndNotLogged() throws Exception {

        ProductDTO productDto = ProductFactory.createProductDto();

        String jsonBody = objMap.writeValueAsString(productDto);

        ResultActions result = mockMvc.perform(post("/products")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteProductShouldDeleteProductWhenLoggedWithAdmin() throws Exception {


        ResultActions result = mockMvc.perform(delete("/products/{id}", existingId)
                .header("Authorization", "Bearer " + bearerToken)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNoContent());
    }

}

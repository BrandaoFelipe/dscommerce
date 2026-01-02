package com_brandao.dscommerce.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com_brandao.dscommerce.dtos.CategoryDTO;
import com_brandao.dscommerce.entities.Category;
import com_brandao.dscommerce.factory.CategoryFactory;
import com_brandao.dscommerce.repositories.CategoryRepository;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {

    @InjectMocks
    private CategoryService service;   

    @Mock
    private CategoryRepository repository;

    private Category category;
    private List<Category> list;

    @BeforeEach
    void setUp() throws Exception{

        category = CategoryFactory.createCategory();
        list = new ArrayList<>();
        list.add(category);

        Mockito.when(repository.findAll()).thenReturn(list);
    }

    @Test
    public void findAllShouldReturnListCategoryDTO(){

        List<CategoryDTO> result = service.findAll();

        Assertions.assertEquals(1, result.size());
        assertEquals(result.get(0).getId(), category.getId());
        assertEquals(result.get(0).getName(), category.getName());
        
    }
}
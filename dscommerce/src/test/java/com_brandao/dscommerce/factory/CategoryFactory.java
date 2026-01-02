package com_brandao.dscommerce.factory;

import com_brandao.dscommerce.entities.Category;

public class CategoryFactory {

    public static Category createCategory() {
        
        return new Category(1L, "teste");
    }

    public static Category createCategory(Long id, String name){
        return new Category(id, name);
    }
}

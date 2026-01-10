package com_brandao.dscommerce.factory;

import com_brandao.dscommerce.entities.Category;

public class CategoryFactory {

    public static Category createCategory() {
        
        return new Category(1L, "teste");
    }
    public static Category createCategory2() {

        return new Category(2L, "teste2");
    }

    public static Category createCategory(Long id, String name){
        return new Category(id, name);
    }
}

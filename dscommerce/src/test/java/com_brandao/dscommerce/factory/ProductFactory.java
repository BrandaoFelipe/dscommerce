package com_brandao.dscommerce.factory;

import com_brandao.dscommerce.entities.Category;
import com_brandao.dscommerce.entities.Product;

public class ProductFactory {
    public static Product createProduct() {
        Category category = CategoryFactory.createCategory();
        Product product = new Product(1L, "teste", "this is a test product", 999.00, "http://imgurl.copm/sdasdasdasdoij54asd5a4s6d84a6sd");
        product.getCategories().add(category);
        return product;
    }
}

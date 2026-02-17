package com_brandao.dscommerce.factory;

import com_brandao.dscommerce.dtos.CategoryDTO;
import com_brandao.dscommerce.dtos.ProductDTO;
import com_brandao.dscommerce.dtos.ProductMinDTO;
import com_brandao.dscommerce.entities.Category;
import com_brandao.dscommerce.entities.Product;

public class ProductFactory {
    public static Product createProduct() {
        Category category = CategoryFactory.createCategory();
        Product product = new Product(1L, "teste", "this is a test product", 999.00, "http://imgurl.copm/sdasdasdasdoij54asd5a4s6d84a6sd");
        product.getCategories().add(category);
        return product;
    }

    public static Product createProduct2() {
        Category category = CategoryFactory.createCategory2();
        Product product = new Product(2L, "teste2", "this is a product2", 100.00, "http://imgurl.copm/sdasdasdasdoij54asd5a4s6d84a6sd");
        product.getCategories().add(category);
        return product;
    }

    public static ProductMinDTO createMinDto() {
        return new ProductMinDTO(createProduct());
    }

    public static ProductDTO createProductDto() {
        CategoryDTO category = new CategoryDTO(CategoryFactory.createCategory2());
        ProductDTO product = new ProductDTO(createProduct());
        product.getCategories().add(category);
        return product;
    }
}

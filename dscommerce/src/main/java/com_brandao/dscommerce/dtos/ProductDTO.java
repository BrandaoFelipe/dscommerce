package com_brandao.dscommerce.dtos;

import java.util.ArrayList;
import java.util.List;

import com_brandao.dscommerce.entities.Category;
import com_brandao.dscommerce.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@AllArgsConstructor
public class ProductDTO {
    
    private Long id;

    @Size(min = 3, max = 80, message = "required")
    @NotBlank
    private String name;    

    @Size(min = 10, message = "min field required is 10 caracters")
    @NotBlank
    private String description;
    
    @Positive(message = "positive number")
    @NotNull(message = "required")
    private Double price;
    
    private String imgUrl;

    @NotEmpty(message = "must have at least one category")
    private List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO() {
    }

    //ModelMapper

    public ProductDTO(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }    

    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();

        for(Category category: entity.getCategories()){

            categories.add(new CategoryDTO(category));
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }
}

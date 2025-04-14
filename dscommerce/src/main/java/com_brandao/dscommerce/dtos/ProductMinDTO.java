package com_brandao.dscommerce.dtos;

import com_brandao.dscommerce.entities.Product;

public class ProductMinDTO {

    private Long id;
  
    private String name;   
   
    private String imgUrl;    
   
    private Double price;

    public ProductMinDTO() {
    }

    public ProductMinDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        imgUrl = entity.getImgUrl();
        price = entity.getPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    
    
    

}

package com_brandao.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com_brandao.dscommerce.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
   
    
}

package com_brandao.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com_brandao.dscommerce.dtos.ProductDTO;
import com_brandao.dscommerce.entities.Product;
import com_brandao.dscommerce.repositories.ProductRepository;
import com_brandao.dscommerce.services.exceptions.DatabaseException;
import com_brandao.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){

        Product product = repository.findById(id).orElseThrow(
                        () -> new ResourceNotFoundException("not found resource"));        
        
                        return new ProductDTO(product);        
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable){

        Page<Product> result = repository.findAll(pageable);        
        return result.map(x -> new ProductDTO(x));        
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto){
        
        Product entity = new Product();

        copyDtoToEntity(entity, dto);

        entity = repository.save(entity);

        return new ProductDTO(entity);

    }

    @Transactional
    public ProductDTO update(ProductDTO dto, Long id){
        
        try
        {
            Product entity = repository.getReferenceById(id);
            copyDtoToEntity(entity, dto);
            entity = repository.save(entity);
            return new ProductDTO(entity);

        } catch(EntityNotFoundException e){
            
            throw new ResourceNotFoundException("resource not found");
        }
        

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){

        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("resource not found");
        }
        try{
            repository.deleteById(id);
        }catch(DataIntegrityViolationException e){
            throw new DatabaseException("referencial integrity failure");
        }
    }


    private void copyDtoToEntity(Product prod, ProductDTO dto){

        prod.setName(dto.getName());
        prod.setDescription(dto.getDescription());
        prod.setPrice(dto.getPrice());
        prod.setImgUrl(dto.getImgUrl());        

    }

}

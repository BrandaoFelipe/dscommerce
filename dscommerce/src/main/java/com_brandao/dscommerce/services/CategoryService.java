package com_brandao.dscommerce.services;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com_brandao.dscommerce.dtos.CategoryDTO;
import com_brandao.dscommerce.entities.Category;

import com_brandao.dscommerce.repositories.CategoryRepository;

import com_brandao.dscommerce.services.exceptions.DatabaseException;
import com_brandao.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id){

        Category entity = repository.findById(id).orElseThrow(
                        () -> new ResourceNotFoundException("not found resource"));        
        
                        return new CategoryDTO(entity);        
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(String name, Pageable pageable){

        Page<Category> result = repository.searchByName(name, pageable);        
        return result.map(x -> new CategoryDTO(x.getId(), x.getName()));
    }

     @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){

        List<Category> result = repository.findAll();
        
        return result.stream().map(x -> new CategoryDTO(x)).toList();
        
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findByName(String name, Pageable pageable){

        Page<Category> result = repository.searchByName(name, pageable);        
        return result.map(x -> new CategoryDTO(x));        
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto){
        
        Category entity = new Category();

        copyDtoToEntity(entity, dto);

        entity = repository.save(entity);

        return new CategoryDTO(entity);

    }

    @Transactional
    public CategoryDTO update(CategoryDTO dto, Long id){
        
        try
        {
            Category entity = repository.getReferenceById(id);
            copyDtoToEntity(entity, dto);
            entity = repository.save(entity);
            return new CategoryDTO(entity);

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


    private void copyDtoToEntity(Category prod, CategoryDTO dto){

        prod.setName(dto.getName());        
    }

}

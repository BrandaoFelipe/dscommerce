package com_brandao.dscommerce.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com_brandao.dscommerce.dtos.ProductDTO;
import com_brandao.dscommerce.dtos.ProductMinDTO;
import com_brandao.dscommerce.services.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService service;

    
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
        
        return ResponseEntity.ok(service.findById(id));
    }    

    @GetMapping
    public ResponseEntity<Page<ProductMinDTO>> findByName(@RequestParam(name = "name", defaultValue = "") String name, Pageable pageable){ 

        return ResponseEntity.ok(service.findByName(name, pageable));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDTO> insert (@RequestBody @Valid ProductDTO productDTO){ 

        productDTO = service.insert(productDTO);
        URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(productDTO.getId())
                    .toUri();

        return ResponseEntity.created(uri).body(productDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@RequestBody @Valid ProductDTO productDTO,  @PathVariable Long id){
        
        return ResponseEntity.ok(service.update(productDTO, id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        
        service.delete(id);
        return ResponseEntity.noContent().build();
    }



}
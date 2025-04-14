package com_brandao.dscommerce.dtos;

import com_brandao.dscommerce.entities.User;

public class ClientDTO {

    private Long id;
    private String nome;
    
    public ClientDTO() {
    }

    public ClientDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public ClientDTO(User entity) {
        id = entity.getId();
        nome = entity.getName();
    }

    public Long getId() {
        return id;
    }  

    public String getNome() {
        return nome;
    }   
    
}

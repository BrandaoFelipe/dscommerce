package com_brandao.dscommerce.services.exceptions;

public class ResourceNotFoundException extends RuntimeException //Não exige bloco try catch
{
    public ResourceNotFoundException(String msg){
        super(msg);
    }
}

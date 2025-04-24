package com_brandao.dscommerce.services.exceptions;

public class ForbiddenException extends RuntimeException //Não exige bloco try catch
{
    public ForbiddenException(String msg){
        super(msg);
    }
}

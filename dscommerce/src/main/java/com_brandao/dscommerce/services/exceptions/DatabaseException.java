package com_brandao.dscommerce.services.exceptions;

public class DatabaseException extends RuntimeException //Não exige bloco try catch
{
    public DatabaseException(String msg){
        super(msg);
    }
}

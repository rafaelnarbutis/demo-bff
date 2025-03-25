package com.br.customer_manager.Exception;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(Long id){
        super("Cliente com id " + id +" nao encontrado ");
    }
}

package com.aditya.documind.Exception;

public class ResourceNotFoundException extends RuntimeException {
 
    public ResourceNotFoundException(String message){
        super(message);
    }
}

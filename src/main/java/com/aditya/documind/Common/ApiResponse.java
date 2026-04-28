package com.aditya.documind.Common;

public record ApiResponse<T>(boolean success,String message,T data){
}

package com.aguusz.ecommerce.utils;

import org.springframework.http.HttpStatus;

public interface IStandardResponse {
    public StandardResponse build(HttpStatus httpStatus, Throwable ex, String message);
}

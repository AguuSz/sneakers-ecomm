package com.aguusz.ecommerce.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class StandardResponseService implements IStandardResponse {
    // Inyector de valores
    @Value("${dev.info.enabled:false}")
    private boolean devInfoEnabled;

    @Override
    public StandardResponse build(HttpStatus httpStatus, Throwable ex, String message) {
        return new StandardResponse(message, ex, httpStatus, this.devInfoEnabled);
    }
}

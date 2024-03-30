package com.aguusz.ecommerce.exceptions;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidRequestException extends Exception{
    private static final long serialVersionUID = 1L;
    @Builder
    public InvalidRequestException(String message, Throwable ex) {
        super(message, ex);
    }

    @Builder
    public InvalidRequestException(String message) {
        super(message);
    }

    @Builder
    public InvalidRequestException(Throwable ex) {
        super(ex.getMessage(), ex);
    }
}

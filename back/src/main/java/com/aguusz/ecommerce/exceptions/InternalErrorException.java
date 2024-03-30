package com.aguusz.ecommerce.exceptions;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InternalErrorException extends Exception{
    private static final long serialVersionUID = 1L;
    @Builder
    public InternalErrorException(String message, Throwable ex) {
        super(message, ex);
    }

    @Builder
    public InternalErrorException(String message) {
        super(message);
    }

    @Builder
    public InternalErrorException(Throwable ex) {
        super(ex.getMessage(), ex);
    }
}

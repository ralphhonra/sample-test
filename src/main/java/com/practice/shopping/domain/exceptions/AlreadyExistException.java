package com.practice.shopping.domain.exceptions;

public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException(String message) { super(message); }
}

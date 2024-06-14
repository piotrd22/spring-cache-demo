package com.example.demo.exceptions;

public class SavingEntityException extends RuntimeException {
    public SavingEntityException() {
        super("Saving entity failed.");
    }
}

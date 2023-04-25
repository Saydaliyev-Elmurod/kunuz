package com.example.kunuz.exps;

public class ItemAlreadyExistsException extends RuntimeException {
    public ItemAlreadyExistsException(String s) {
        super(s);
    }

    public ItemAlreadyExistsException() {
        super();
    }
}

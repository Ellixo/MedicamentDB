package com.ellixo.healthcare.exception;

public class UnknownObjectException extends RuntimeException {

    public UnknownObjectException(String object, String id) {
        super("unknown " + object + " - id :" + id);
    }
}

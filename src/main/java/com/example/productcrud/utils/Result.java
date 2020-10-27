package com.example.productcrud.utils;

public class Result {

    private final int code;
    private final String message;

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

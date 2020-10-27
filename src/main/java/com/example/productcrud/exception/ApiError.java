package com.example.productcrud.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.example.productcrud.utils.Constants.SPACE;

@Getter
@Setter
@AllArgsConstructor
public class ApiError {

    private HttpStatus status;
    private String message;
    private List<String> errors;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(message);
        errors.stream().forEach(er->{
             builder.append(SPACE).append(er);
        });
        return builder.toString();
    }
}
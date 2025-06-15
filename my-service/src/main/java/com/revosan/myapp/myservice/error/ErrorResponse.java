package com.revosan.myapp.myservice.error; // Updated package name

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class ErrorResponse {

    @Builder.Default
    private final LocalDateTime timestamp = LocalDateTime.now();

    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private final Map<String, String> errors;

}

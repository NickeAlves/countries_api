package com.countries.exception;

import java.time.LocalDateTime;

public record ErrorResponse(int status, String error, String message, String path ,LocalDateTime timestamp) {
}

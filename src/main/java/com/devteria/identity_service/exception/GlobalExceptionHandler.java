package com.devteria.identity_service.exception;

import com.devteria.identity_service.dto.response.ApiResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse<Void>> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        var response = new ApiResponse<Void>();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(response);
    }


    @ExceptionHandler(value = AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handlingAccessDeniedException(AuthorizationDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ApiResponse.<Void>builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handlingValidation(MethodArgumentNotValidException e) {
        String enumKey = e.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY; ;

        try {
             errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException ex) {
        }

        var constraintViolation = e.getBindingResult().getAllErrors()
                .getFirst().unwrap(ConstraintViolation.class);
        Map<String, Object> attributes = constraintViolation.getConstraintDescriptor().getAttributes();

        var response = new ApiResponse<Void>();
        response.setCode(errorCode.getCode());
        response.setMessage(mapAttribute(errorCode.getMessage(), attributes));
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<Void>> handlingRuntimeException(Exception exception) {
        log.info("Exception" + exception);
        var response = new ApiResponse<Void>();
        response.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        response.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(response);
    }


    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get("min"));
        return message.replace("{"+ MIN_ATTRIBUTE +"}", minValue);
    }

}

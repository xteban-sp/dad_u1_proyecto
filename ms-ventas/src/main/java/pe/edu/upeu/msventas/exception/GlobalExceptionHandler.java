package pe.edu.upeu.msventas.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND", ex.getMessage(), request, List.of());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusiness(BusinessException ex, HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, "BUSINESS_RULE_VIOLATION", ex.getMessage(), request, List.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<FieldValidationError> details = ex.getBindingResult().getFieldErrors().stream()
                .map(field -> new FieldValidationError(field.getField(), field.getDefaultMessage(), field.getRejectedValue()))
                .toList();

        List<FieldValidationError> objectLevelDetails = ex.getBindingResult().getGlobalErrors().stream()
                .map(error -> new FieldValidationError(error.getObjectName(), error.getDefaultMessage(), null))
                .toList();

        List<FieldValidationError> merged = java.util.stream.Stream.concat(details.stream(), objectLevelDetails.stream()).toList();
        return build(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "Revise los campos enviados", request, merged);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest request) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", ex.getMessage(), request, List.of());
    }

    private ResponseEntity<ApiError> build(
            HttpStatus status,
            String code,
            String message,
            HttpServletRequest request,
            List<FieldValidationError> fieldErrors) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                status.value(),
                code,
                message,
                request.getRequestURI(),
                request.getMethod(),
                UUID.randomUUID().toString(),
                fieldErrors);
        return ResponseEntity.status(status).body(apiError);
    }
}

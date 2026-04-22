package pe.edu.upeu.msproducto.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(
        LocalDateTime timestamp,
        int status,
        String code,
        String message,
        String path,
        String method,
        String traceId,
        List<FieldValidationError> fieldErrors) {
}

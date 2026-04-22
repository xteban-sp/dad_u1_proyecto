package pe.edu.upeu.msventas.exception;

public record FieldValidationError(String field, String message, Object rejectedValue) {
}

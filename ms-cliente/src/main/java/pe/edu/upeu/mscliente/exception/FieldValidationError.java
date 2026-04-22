package pe.edu.upeu.mscliente.exception;

public record FieldValidationError(String field, String message, Object rejectedValue) {
}

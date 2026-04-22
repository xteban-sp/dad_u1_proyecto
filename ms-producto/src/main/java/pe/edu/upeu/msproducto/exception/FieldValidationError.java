package pe.edu.upeu.msproducto.exception;

public record FieldValidationError(String field, String message, Object rejectedValue) {
}

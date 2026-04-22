package pe.edu.upeu.mscliente.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Locale;
import pe.edu.upeu.mscliente.dto.ClienteRequestDTO;

public class DocumentoValidator implements ConstraintValidator<ValidDocumento, ClienteRequestDTO> {

    @Override
    public boolean isValid(ClienteRequestDTO value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        String tipo = normalize(value.getTipoDocumento());
        String numero = normalize(value.getNumeroDocumento());

        if (tipo == null || numero == null) {
            return true;
        }

        context.disableDefaultConstraintViolation();

        return switch (tipo) {
            case "DNI" -> validateDigits(numero, 8, "numeroDocumento", "Para DNI debe tener exactamente 8 digitos", context);
            case "CE" -> validateAlphaNumeric(numero, 9, "numeroDocumento", "Para CE debe tener exactamente 9 caracteres alfanumericos", context);
            case "RUC" -> validateDigits(numero, 11, "numeroDocumento", "Para RUC debe tener exactamente 11 digitos", context);
            default -> addViolation("tipoDocumento", "tipoDocumento solo permite DNI, CE o RUC", context);
        };
    }

    private boolean validateDigits(String value, int length, String field, String message, ConstraintValidatorContext context) {
        if (value.length() != length || !value.matches("\\d{" + length + "}")) {
            return addViolation(field, message, context);
        }
        return true;
    }

    private boolean validateAlphaNumeric(String value, int length, String field, String message, ConstraintValidatorContext context) {
        if (value.length() != length || !value.matches("[A-Z0-9]{" + length + "}")) {
            return addViolation(field, message, context);
        }
        return true;
    }

    private boolean addViolation(String field, String message, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(field)
                .addConstraintViolation();
        return false;
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim().toUpperCase(Locale.ROOT);
        return normalized.isEmpty() ? null : normalized;
    }
}

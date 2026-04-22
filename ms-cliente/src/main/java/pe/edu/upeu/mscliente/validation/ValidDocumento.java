package pe.edu.upeu.mscliente.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DocumentoValidator.class)
public @interface ValidDocumento {

    String message() default "Documento invalido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

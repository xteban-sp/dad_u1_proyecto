package pe.edu.upeu.mscliente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import pe.edu.upeu.mscliente.validation.ValidDocumento;

@ValidDocumento
@Data
public class ClienteRequestDTO {

    @NotBlank(message = "El tipo de documento es obligatorio")
    @Size(max = 3, message = "tipoDocumento debe tener maximo 3 caracteres")
    private String tipoDocumento;

    @NotBlank(message = "El numero de documento es obligatorio")
    @Size(min = 8, max = 11, message = "numeroDocumento debe tener entre 8 y 11 caracteres")
    private String numeroDocumento;

    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 80)
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 100)
    private String apellidos;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no es valido")
    @Size(max = 120)
    private String correo;

    @NotBlank(message = "El telefono es obligatorio")
    @Size(max = 20)
    private String telefono;

    @NotBlank(message = "La direccion es obligatoria")
    @Size(max = 180)
    private String direccion;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
}

package pe.edu.upeu.mscliente.dto;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ClienteResponseDTO(
        Long idCliente,
        String tipoDocumento,
        String numeroDocumento,
        String nombres,
        String apellidos,
        String correo,
        String telefono,
        String direccion,
        LocalDate fechaRegistro,
        Boolean estado) {
}

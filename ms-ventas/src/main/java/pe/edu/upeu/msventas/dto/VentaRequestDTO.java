package pe.edu.upeu.msventas.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
public class VentaRequestDTO {

    @NotNull(message = "El id del cliente es obligatorio")
    private Long idCliente;

    @NotEmpty(message = "La venta debe tener al menos un detalle")
    @Valid
    private List<DetalleVentaRequestDTO> detalles;
}

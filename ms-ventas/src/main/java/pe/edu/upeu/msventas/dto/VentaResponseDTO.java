package pe.edu.upeu.msventas.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record VentaResponseDTO(
        Long idVenta,
        Long idCliente,
        LocalDateTime fechaVenta,
        BigDecimal total,
        String estado,
        List<DetalleVentaResponseDTO> detalles) {
}

package pe.edu.upeu.msventas.dto;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record DetalleVentaResponseDTO(
        Long idDetalleVenta,
        Long idProducto,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotal) {
}

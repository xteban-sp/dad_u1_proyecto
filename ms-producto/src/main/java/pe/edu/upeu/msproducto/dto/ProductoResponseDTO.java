package pe.edu.upeu.msproducto.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ProductoResponseDTO(
        Long idProducto,
        String codigo,
        String nombre,
        String descripcion,
        CategoriaResponseDTO categoria,
        BigDecimal precio,
        Integer stock,
        LocalDate fechaCreacion,
        Boolean estado) {
}

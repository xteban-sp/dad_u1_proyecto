package pe.edu.upeu.msproducto.dto;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record CategoriaResponseDTO(
        Long idCategoria,
        String nombre,
        String descripcion,
        LocalDate fechaCreacion,
        Boolean estado) {
}

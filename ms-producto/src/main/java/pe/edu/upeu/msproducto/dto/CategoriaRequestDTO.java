package pe.edu.upeu.msproducto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoriaRequestDTO {

    @NotBlank(message = "El nombre de la categoria es obligatorio")
    @Size(max = 80)
    private String nombre;

    @NotBlank(message = "La descripcion es obligatoria")
    @Size(max = 250)
    private String descripcion;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
}

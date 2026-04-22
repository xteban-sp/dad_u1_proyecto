package pe.edu.upeu.msproducto.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductoRequestDTO {

    @NotBlank(message = "El codigo es obligatorio")
    @Size(max = 40)
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 120)
    private String nombre;

    @NotBlank(message = "La descripcion es obligatoria")
    @Size(max = 400)
    private String descripcion;

    @NotNull(message = "La categoria es obligatoria")
    private Long idCategoria;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
    private BigDecimal precio;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
}

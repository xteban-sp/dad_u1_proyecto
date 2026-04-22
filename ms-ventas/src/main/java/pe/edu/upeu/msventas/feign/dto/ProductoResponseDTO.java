package pe.edu.upeu.msventas.feign.dto;

import java.math.BigDecimal;

public record ProductoResponseDTO(Long idProducto, String codigo, String nombre, BigDecimal precio, Integer stock, Boolean estado) {
}

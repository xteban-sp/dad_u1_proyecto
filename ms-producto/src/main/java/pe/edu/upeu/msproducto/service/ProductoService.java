package pe.edu.upeu.msproducto.service;

import java.util.List;
import pe.edu.upeu.msproducto.dto.ProductoRequestDTO;
import pe.edu.upeu.msproducto.dto.ProductoResponseDTO;

public interface ProductoService {

    List<ProductoResponseDTO> listar();

    ProductoResponseDTO obtenerPorId(Long idProducto);

    ProductoResponseDTO obtenerPorCodigo(String codigo);

    ProductoResponseDTO crear(ProductoRequestDTO request);

    ProductoResponseDTO actualizar(Long idProducto, ProductoRequestDTO request);

    void eliminar(Long idProducto);

    Integer obtenerStock(Long idProducto);

    Integer reducirStock(Long idProducto, Integer cantidad);

    Integer aumentarStock(Long idProducto, Integer cantidad);
}

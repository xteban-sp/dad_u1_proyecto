package pe.edu.upeu.msproducto.service;

import java.util.List;
import pe.edu.upeu.msproducto.dto.CategoriaRequestDTO;
import pe.edu.upeu.msproducto.dto.CategoriaResponseDTO;

public interface CategoriaService {

    List<CategoriaResponseDTO> listar();

    CategoriaResponseDTO obtenerPorId(Long idCategoria);

    CategoriaResponseDTO crear(CategoriaRequestDTO request);

    CategoriaResponseDTO actualizar(Long idCategoria, CategoriaRequestDTO request);

    void eliminar(Long idCategoria);
}

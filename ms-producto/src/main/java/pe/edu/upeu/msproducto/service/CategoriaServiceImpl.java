package pe.edu.upeu.msproducto.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.msproducto.dto.CategoriaRequestDTO;
import pe.edu.upeu.msproducto.dto.CategoriaResponseDTO;
import pe.edu.upeu.msproducto.entity.Categoria;
import pe.edu.upeu.msproducto.exception.BusinessException;
import pe.edu.upeu.msproducto.exception.ResourceNotFoundException;
import pe.edu.upeu.msproducto.repository.CategoriaRepository;
import pe.edu.upeu.msproducto.repository.ProductoRepository;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listar() {
        return categoriaRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponseDTO obtenerPorId(Long idCategoria) {
        return toResponse(getEntityById(idCategoria));
    }

    @Override
    @Transactional
    public CategoriaResponseDTO crear(CategoriaRequestDTO request) {
        if (categoriaRepository.existsByNombre(request.getNombre())) {
            throw new BusinessException("El nombre de categoria ya existe");
        }

        Categoria categoria = new Categoria();
        mapToEntity(categoria, request);
        return toResponse(categoriaRepository.save(categoria));
    }

    @Override
    @Transactional
    public CategoriaResponseDTO actualizar(Long idCategoria, CategoriaRequestDTO request) {
        Categoria categoria = getEntityById(idCategoria);
        if (categoriaRepository.existsByNombreAndIdCategoriaNot(request.getNombre(), idCategoria)) {
            throw new BusinessException("El nombre de categoria ya esta en uso");
        }

        mapToEntity(categoria, request);
        return toResponse(categoriaRepository.save(categoria));
    }

    @Override
    @Transactional
    public void eliminar(Long idCategoria) {
        Categoria categoria = getEntityById(idCategoria);
        if (productoRepository.existsByCategoria_IdCategoria(idCategoria)) {
            throw new BusinessException("No se puede eliminar la categoria porque tiene productos asociados");
        }
        categoriaRepository.delete(categoria);
    }

    private Categoria getEntityById(Long idCategoria) {
        return categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con id " + idCategoria));
    }

    private void mapToEntity(Categoria categoria, CategoriaRequestDTO request) {
        categoria.setNombre(request.getNombre());
        categoria.setDescripcion(request.getDescripcion());
        categoria.setEstado(request.getEstado());
    }

    private CategoriaResponseDTO toResponse(Categoria categoria) {
        return CategoriaResponseDTO.builder()
                .idCategoria(categoria.getIdCategoria())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .fechaCreacion(categoria.getFechaCreacion())
                .estado(categoria.getEstado())
                .build();
    }
}

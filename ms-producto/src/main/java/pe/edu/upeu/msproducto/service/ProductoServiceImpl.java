package pe.edu.upeu.msproducto.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.msproducto.dto.CategoriaResponseDTO;
import pe.edu.upeu.msproducto.dto.ProductoRequestDTO;
import pe.edu.upeu.msproducto.dto.ProductoResponseDTO;
import pe.edu.upeu.msproducto.entity.Categoria;
import pe.edu.upeu.msproducto.entity.Producto;
import pe.edu.upeu.msproducto.exception.BusinessException;
import pe.edu.upeu.msproducto.exception.ResourceNotFoundException;
import pe.edu.upeu.msproducto.repository.CategoriaRepository;
import pe.edu.upeu.msproducto.repository.ProductoRepository;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> listar() {
        return productoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponseDTO obtenerPorId(Long idProducto) {
        return toResponse(getEntityById(idProducto));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponseDTO obtenerPorCodigo(String codigo) {
        Producto producto = productoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con codigo " + codigo));
        return toResponse(producto);
    }

    @Override
    @Transactional
    public ProductoResponseDTO crear(ProductoRequestDTO request) {
        if (productoRepository.existsByCodigo(request.getCodigo())) {
            throw new BusinessException("El codigo del producto ya existe");
        }
        Producto producto = mapToEntity(new Producto(), request);
        return toResponse(productoRepository.save(producto));
    }

    @Override
    @Transactional
    public ProductoResponseDTO actualizar(Long idProducto, ProductoRequestDTO request) {
        Producto producto = getEntityById(idProducto);

        if (productoRepository.existsByCodigoAndIdProductoNot(request.getCodigo(), idProducto)) {
            throw new BusinessException("El codigo del producto ya esta registrado por otro producto");
        }

        mapToEntity(producto, request);
        return toResponse(productoRepository.save(producto));
    }

    @Override
    @Transactional
    public void eliminar(Long idProducto) {
        Producto producto = getEntityById(idProducto);
        productoRepository.delete(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer obtenerStock(Long idProducto) {
        return getEntityById(idProducto).getStock();
    }

    @Override
    @Transactional
    public Integer reducirStock(Long idProducto, Integer cantidad) {
        Producto producto = getEntityById(idProducto);
        int nuevoStock = producto.getStock() - cantidad;
        if (nuevoStock < 0) {
            throw new BusinessException("Stock insuficiente para el producto " + idProducto);
        }
        producto.setStock(nuevoStock);
        productoRepository.save(producto);
        return nuevoStock;
    }

    @Override
    @Transactional
    public Integer aumentarStock(Long idProducto, Integer cantidad) {
        Producto producto = getEntityById(idProducto);
        int nuevoStock = producto.getStock() + cantidad;
        producto.setStock(nuevoStock);
        productoRepository.save(producto);
        return nuevoStock;
    }

    private Producto getEntityById(Long idProducto) {
        return productoRepository.findById(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id " + idProducto));
    }

    private Producto mapToEntity(Producto producto, ProductoRequestDTO request) {
        producto.setCodigo(request.getCodigo());
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setCategoria(getCategoriaById(request.getIdCategoria()));
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setEstado(request.getEstado());
        return producto;
    }

    private Categoria getCategoriaById(Long idCategoria) {
        return categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con id " + idCategoria));
    }

    private ProductoResponseDTO toResponse(Producto producto) {
        return ProductoResponseDTO.builder()
                .idProducto(producto.getIdProducto())
                .codigo(producto.getCodigo())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .categoria(CategoriaResponseDTO.builder()
                        .idCategoria(producto.getCategoria().getIdCategoria())
                        .nombre(producto.getCategoria().getNombre())
                        .descripcion(producto.getCategoria().getDescripcion())
                        .fechaCreacion(producto.getCategoria().getFechaCreacion())
                        .estado(producto.getCategoria().getEstado())
                        .build())
                .precio(producto.getPrecio())
                .stock(producto.getStock())
                .fechaCreacion(producto.getFechaCreacion())
                .estado(producto.getEstado())
                .build();
    }
}

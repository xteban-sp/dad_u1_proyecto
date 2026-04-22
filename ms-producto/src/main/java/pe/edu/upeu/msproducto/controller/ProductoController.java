package pe.edu.upeu.msproducto.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upeu.msproducto.dto.ProductoRequestDTO;
import pe.edu.upeu.msproducto.dto.ProductoResponseDTO;
import pe.edu.upeu.msproducto.dto.StockRequestDTO;
import pe.edu.upeu.msproducto.service.ProductoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public List<ProductoResponseDTO> listar() {
        return productoService.listar();
    }

    @GetMapping("/{id}")
    public ProductoResponseDTO obtenerPorId(@PathVariable("id") Long idProducto) {
        return productoService.obtenerPorId(idProducto);
    }

    @GetMapping("/codigo/{codigo}")
    public ProductoResponseDTO obtenerPorCodigo(@PathVariable String codigo) {
        return productoService.obtenerPorCodigo(codigo);
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.crear(request));
    }

    @PutMapping("/{id}")
    public ProductoResponseDTO actualizar(@PathVariable("id") Long idProducto, @Valid @RequestBody ProductoRequestDTO request) {
        return productoService.actualizar(idProducto, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long idProducto) {
        productoService.eliminar(idProducto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/stock")
    public Integer obtenerStock(@PathVariable("id") Long idProducto) {
        return productoService.obtenerStock(idProducto);
    }

    @PutMapping("/{id}/reducir-stock")
    public Integer reducirStock(@PathVariable("id") Long idProducto, @Valid @RequestBody StockRequestDTO request) {
        return productoService.reducirStock(idProducto, request.getCantidad());
    }

    @PutMapping("/{id}/aumentar-stock")
    public Integer aumentarStock(@PathVariable("id") Long idProducto, @Valid @RequestBody StockRequestDTO request) {
        return productoService.aumentarStock(idProducto, request.getCantidad());
    }
}

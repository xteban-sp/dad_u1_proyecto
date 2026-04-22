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
import pe.edu.upeu.msproducto.dto.CategoriaRequestDTO;
import pe.edu.upeu.msproducto.dto.CategoriaResponseDTO;
import pe.edu.upeu.msproducto.service.CategoriaService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public List<CategoriaResponseDTO> listar() {
        return categoriaService.listar();
    }

    @GetMapping("/{id}")
    public CategoriaResponseDTO obtenerPorId(@PathVariable("id") Long idCategoria) {
        return categoriaService.obtenerPorId(idCategoria);
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> crear(@Valid @RequestBody CategoriaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.crear(request));
    }

    @PutMapping("/{id}")
    public CategoriaResponseDTO actualizar(@PathVariable("id") Long idCategoria, @Valid @RequestBody CategoriaRequestDTO request) {
        return categoriaService.actualizar(idCategoria, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long idCategoria) {
        categoriaService.eliminar(idCategoria);
        return ResponseEntity.noContent().build();
    }
}

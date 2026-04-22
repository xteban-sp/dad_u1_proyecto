package pe.edu.upeu.msventas.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upeu.msventas.dto.VentaRequestDTO;
import pe.edu.upeu.msventas.dto.VentaResponseDTO;
import pe.edu.upeu.msventas.service.VentaService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    public List<VentaResponseDTO> listar() {
        return ventaService.listar();
    }

    @GetMapping("/{id}")
    public VentaResponseDTO obtenerPorId(@PathVariable("id") Long idVenta) {
        return ventaService.obtenerPorId(idVenta);
    }

    @GetMapping("/cliente/{idCliente}")
    public List<VentaResponseDTO> listarPorCliente(@PathVariable Long idCliente) {
        return ventaService.listarPorCliente(idCliente);
    }

    @PostMapping
    public ResponseEntity<VentaResponseDTO> registrar(@Valid @RequestBody VentaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.registrar(request));
    }

    @PutMapping("/{id}/anular")
    public VentaResponseDTO anular(@PathVariable("id") Long idVenta) {
        return ventaService.anular(idVenta);
    }
}

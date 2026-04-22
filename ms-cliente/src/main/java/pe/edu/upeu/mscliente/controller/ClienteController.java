package pe.edu.upeu.mscliente.controller;

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
import pe.edu.upeu.mscliente.dto.ClienteRequestDTO;
import pe.edu.upeu.mscliente.dto.ClienteResponseDTO;
import pe.edu.upeu.mscliente.service.ClienteService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public List<ClienteResponseDTO> listar() {
        return clienteService.listar();
    }

    @GetMapping("/{id}")
    public ClienteResponseDTO obtenerPorId(@PathVariable("id") Long idCliente) {
        return clienteService.obtenerPorId(idCliente);
    }

    @GetMapping("/documento/{numero}")
    public ClienteResponseDTO obtenerPorDocumento(@PathVariable("numero") String numeroDocumento) {
        return clienteService.obtenerPorNumeroDocumento(numeroDocumento);
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crear(@Valid @RequestBody ClienteRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.crear(request));
    }

    @PutMapping("/{id}")
    public ClienteResponseDTO actualizar(@PathVariable("id") Long idCliente, @Valid @RequestBody ClienteRequestDTO request) {
        return clienteService.actualizar(idCliente, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long idCliente) {
        clienteService.eliminar(idCliente);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/existe")
    public boolean existe(@PathVariable("id") Long idCliente) {
        return clienteService.existe(idCliente);
    }
}

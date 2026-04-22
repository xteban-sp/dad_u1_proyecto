package pe.edu.upeu.msventas.service;

import java.util.List;
import pe.edu.upeu.msventas.dto.VentaRequestDTO;
import pe.edu.upeu.msventas.dto.VentaResponseDTO;

public interface VentaService {

    List<VentaResponseDTO> listar();

    VentaResponseDTO obtenerPorId(Long idVenta);

    List<VentaResponseDTO> listarPorCliente(Long idCliente);

    VentaResponseDTO registrar(VentaRequestDTO request);

    VentaResponseDTO anular(Long idVenta);
}

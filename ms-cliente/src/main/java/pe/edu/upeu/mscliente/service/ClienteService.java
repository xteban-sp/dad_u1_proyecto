package pe.edu.upeu.mscliente.service;

import java.util.List;
import pe.edu.upeu.mscliente.dto.ClienteRequestDTO;
import pe.edu.upeu.mscliente.dto.ClienteResponseDTO;

public interface ClienteService {

    List<ClienteResponseDTO> listar();

    ClienteResponseDTO obtenerPorId(Long idCliente);

    ClienteResponseDTO obtenerPorNumeroDocumento(String numeroDocumento);

    ClienteResponseDTO crear(ClienteRequestDTO request);

    ClienteResponseDTO actualizar(Long idCliente, ClienteRequestDTO request);

    void eliminar(Long idCliente);

    boolean existe(Long idCliente);
}

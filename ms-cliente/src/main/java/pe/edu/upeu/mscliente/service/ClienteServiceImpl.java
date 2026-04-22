package pe.edu.upeu.mscliente.service;

import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.mscliente.dto.ClienteRequestDTO;
import pe.edu.upeu.mscliente.dto.ClienteResponseDTO;
import pe.edu.upeu.mscliente.entity.Cliente;
import pe.edu.upeu.mscliente.exception.BusinessException;
import pe.edu.upeu.mscliente.exception.ResourceNotFoundException;
import pe.edu.upeu.mscliente.repository.ClienteRepository;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listar() {
        return clienteRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO obtenerPorId(Long idCliente) {
        return toResponse(getEntityById(idCliente));
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO obtenerPorNumeroDocumento(String numeroDocumento) {
        Cliente cliente = clienteRepository.findByNumeroDocumento(numeroDocumento)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con documento " + numeroDocumento));
        return toResponse(cliente);
    }

    @Override
    @Transactional
    public ClienteResponseDTO crear(ClienteRequestDTO request) {
        if (clienteRepository.existsByNumeroDocumento(request.getNumeroDocumento())) {
            throw new BusinessException("El numero de documento ya existe");
        }

        Cliente cliente = mapToEntity(new Cliente(), request);
        return toResponse(clienteRepository.save(cliente));
    }

    @Override
    @Transactional
    public ClienteResponseDTO actualizar(Long idCliente, ClienteRequestDTO request) {
        Cliente cliente = getEntityById(idCliente);

        if (clienteRepository.existsByNumeroDocumentoAndIdClienteNot(request.getNumeroDocumento(), idCliente)) {
            throw new BusinessException("El numero de documento ya esta registrado por otro cliente");
        }

        mapToEntity(cliente, request);
        return toResponse(clienteRepository.save(cliente));
    }

    @Override
    @Transactional
    public void eliminar(Long idCliente) {
        Cliente cliente = getEntityById(idCliente);
        clienteRepository.delete(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existe(Long idCliente) {
        return clienteRepository.existsById(idCliente);
    }

    private Cliente getEntityById(Long idCliente) {
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id " + idCliente));
    }

    private Cliente mapToEntity(Cliente cliente, ClienteRequestDTO request) {
        cliente.setTipoDocumento(request.getTipoDocumento().trim().toUpperCase(Locale.ROOT));
        cliente.setNumeroDocumento(request.getNumeroDocumento().trim().toUpperCase(Locale.ROOT));
        cliente.setNombres(request.getNombres());
        cliente.setApellidos(request.getApellidos());
        cliente.setCorreo(request.getCorreo());
        cliente.setTelefono(request.getTelefono());
        cliente.setDireccion(request.getDireccion());
        cliente.setEstado(request.getEstado());
        return cliente;
    }

    private ClienteResponseDTO toResponse(Cliente cliente) {
        return ClienteResponseDTO.builder()
                .idCliente(cliente.getIdCliente())
                .tipoDocumento(cliente.getTipoDocumento())
                .numeroDocumento(cliente.getNumeroDocumento())
                .nombres(cliente.getNombres())
                .apellidos(cliente.getApellidos())
                .correo(cliente.getCorreo())
                .telefono(cliente.getTelefono())
                .direccion(cliente.getDireccion())
                .fechaRegistro(cliente.getFechaRegistro())
                .estado(cliente.getEstado())
                .build();
    }
}

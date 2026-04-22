package pe.edu.upeu.msventas.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.msventas.dto.DetalleVentaRequestDTO;
import pe.edu.upeu.msventas.dto.DetalleVentaResponseDTO;
import pe.edu.upeu.msventas.dto.VentaRequestDTO;
import pe.edu.upeu.msventas.dto.VentaResponseDTO;
import pe.edu.upeu.msventas.entity.DetalleVenta;
import pe.edu.upeu.msventas.entity.Venta;
import pe.edu.upeu.msventas.exception.BusinessException;
import pe.edu.upeu.msventas.exception.ResourceNotFoundException;
import pe.edu.upeu.msventas.feign.CantidadRequest;
import pe.edu.upeu.msventas.feign.ClienteFeignClient;
import pe.edu.upeu.msventas.feign.ProductoFeignClient;
import pe.edu.upeu.msventas.feign.dto.ProductoResponseDTO;
import pe.edu.upeu.msventas.repository.VentaRepository;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteFeignClient clienteFeignClient;
    private final ProductoFeignClient productoFeignClient;

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponseDTO> listar() {
        return ventaRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VentaResponseDTO obtenerPorId(Long idVenta) {
        return toResponse(getEntityById(idVenta));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponseDTO> listarPorCliente(Long idCliente) {
        return ventaRepository.findByIdCliente(idCliente).stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public VentaResponseDTO registrar(VentaRequestDTO request) {
        if (!clienteFeignClient.existeCliente(request.getIdCliente())) {
            throw new BusinessException("No existe el cliente con id " + request.getIdCliente());
        }

        Venta venta = Venta.builder()
                .idCliente(request.getIdCliente())
                .estado("REGISTRADA")
                .build();

        List<DetalleVenta> detalles = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (DetalleVentaRequestDTO detalleRequest : request.getDetalles()) {
            ProductoResponseDTO producto = productoFeignClient.obtenerProducto(detalleRequest.getIdProducto());
            if (producto == null || Boolean.FALSE.equals(producto.estado())) {
                throw new BusinessException("Producto invalido o inactivo: " + detalleRequest.getIdProducto());
            }

            Integer stock = productoFeignClient.obtenerStock(detalleRequest.getIdProducto());
            if (stock < detalleRequest.getCantidad()) {
                throw new BusinessException("No hay stock suficiente para el producto " + detalleRequest.getIdProducto());
            }

            BigDecimal subtotal = producto.precio().multiply(BigDecimal.valueOf(detalleRequest.getCantidad()));

            DetalleVenta detalle = DetalleVenta.builder()
                    .idProducto(detalleRequest.getIdProducto())
                    .cantidad(detalleRequest.getCantidad())
                    .precioUnitario(producto.precio())
                    .subtotal(subtotal)
                    .venta(venta)
                    .build();

            detalles.add(detalle);
            total = total.add(subtotal);
        }

        venta.setTotal(total);
        venta.setDetalles(detalles);

        Venta saved = ventaRepository.save(venta);

        for (DetalleVenta detalle : detalles) {
            productoFeignClient.reducirStock(detalle.getIdProducto(), new CantidadRequest(detalle.getCantidad()));
        }

        return toResponse(saved);
    }

    @Override
    @Transactional
    public VentaResponseDTO anular(Long idVenta) {
        Venta venta = getEntityById(idVenta);
        if ("ANULADA".equalsIgnoreCase(venta.getEstado())) {
            throw new BusinessException("La venta ya esta anulada");
        }

        venta.setEstado("ANULADA");
        Venta saved = ventaRepository.save(venta);

        for (DetalleVenta detalle : saved.getDetalles()) {
            productoFeignClient.aumentarStock(detalle.getIdProducto(), new CantidadRequest(detalle.getCantidad()));
        }

        return toResponse(saved);
    }

    private Venta getEntityById(Long idVenta) {
        return ventaRepository.findById(idVenta)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con id " + idVenta));
    }

    private VentaResponseDTO toResponse(Venta venta) {
        List<DetalleVentaResponseDTO> detalleResponse = venta.getDetalles().stream()
                .map(detalle -> DetalleVentaResponseDTO.builder()
                        .idDetalleVenta(detalle.getIdDetalleVenta())
                        .idProducto(detalle.getIdProducto())
                        .cantidad(detalle.getCantidad())
                        .precioUnitario(detalle.getPrecioUnitario())
                        .subtotal(detalle.getSubtotal())
                        .build())
                .toList();

        return VentaResponseDTO.builder()
                .idVenta(venta.getIdVenta())
                .idCliente(venta.getIdCliente())
                .fechaVenta(venta.getFechaVenta())
                .total(venta.getTotal())
                .estado(venta.getEstado())
                .detalles(detalleResponse)
                .build();
    }
}

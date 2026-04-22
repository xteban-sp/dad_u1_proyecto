package pe.edu.upeu.msventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.msventas.entity.DetalleVenta;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
}

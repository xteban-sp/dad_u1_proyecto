package pe.edu.upeu.msventas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.msventas.entity.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByIdCliente(Long idCliente);
}

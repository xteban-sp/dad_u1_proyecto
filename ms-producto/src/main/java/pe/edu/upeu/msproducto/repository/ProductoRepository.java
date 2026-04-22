package pe.edu.upeu.msproducto.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.msproducto.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    boolean existsByCodigoAndIdProductoNot(String codigo, Long idProducto);

    boolean existsByCategoria_IdCategoria(Long idCategoria);
}

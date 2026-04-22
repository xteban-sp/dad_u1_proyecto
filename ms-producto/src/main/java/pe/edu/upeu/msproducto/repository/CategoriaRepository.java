package pe.edu.upeu.msproducto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.msproducto.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    boolean existsByNombre(String nombre);

    boolean existsByNombreAndIdCategoriaNot(String nombre, Long idCategoria);
}

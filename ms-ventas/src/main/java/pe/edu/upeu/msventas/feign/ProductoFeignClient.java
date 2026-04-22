package pe.edu.upeu.msventas.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pe.edu.upeu.msventas.feign.dto.ProductoResponseDTO;

@FeignClient(name = "ms-producto")
public interface ProductoFeignClient {

    @GetMapping("/productos/{id}")
    ProductoResponseDTO obtenerProducto(@PathVariable("id") Long idProducto);

    @GetMapping("/productos/{id}/stock")
    Integer obtenerStock(@PathVariable("id") Long idProducto);

    @PutMapping("/productos/{id}/reducir-stock")
    Integer reducirStock(@PathVariable("id") Long idProducto, @RequestBody CantidadRequest cantidadRequest);

    @PutMapping("/productos/{id}/aumentar-stock")
    Integer aumentarStock(@PathVariable("id") Long idProducto, @RequestBody CantidadRequest cantidadRequest);
}

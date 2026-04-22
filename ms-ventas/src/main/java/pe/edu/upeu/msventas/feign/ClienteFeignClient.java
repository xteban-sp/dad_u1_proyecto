package pe.edu.upeu.msventas.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-cliente")
public interface ClienteFeignClient {

    @GetMapping("/clientes/{id}/existe")
    boolean existeCliente(@PathVariable("id") Long idCliente);
}

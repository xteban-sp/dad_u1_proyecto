# Backend SGSU - Microservicios

Base integrada para:

- `service-registry` (Eureka Server)
- `config-service` (Spring Cloud Config Server)
- `api-gateway` (Gateway de entrada)
- `ms-cliente`
- `ms-producto`
- `ms-ventas`

## Paquetes usados

- `pe.edu.upeu.serviceregistry`
- `pe.edu.upeu.configservice`
- `pe.edu.upeu.apigateway`
- `pe.edu.upeu.mscliente`
- `pe.edu.upeu.msproducto`
- `pe.edu.upeu.msventas`

## Orden recomendado de arranque

1. `service-registry` (puerto `8761`)
2. `config-service` (puerto `8888`)
3. `api-gateway` (puerto `8080`)
4. `ms-cliente` (puerto `8081`)
5. `ms-producto` (puerto `8082`)
6. `ms-ventas` (puerto `8083`)

## Endpoints principales

### Gateway

- `GET /api/clientes/**`
- `GET /api/productos/**`
- `GET /api/ventas/**`

### Microservicios

- `ms-cliente`: `/clientes`
- `ms-producto`: `/productos`
- `ms-ventas`: `/ventas`

## Configuraciones centralizadas

El `config-service` usa perfil `native` y archivos en:

- `config-service/src/main/resources/configurations/api-gateway.properties`
- `config-service/src/main/resources/configurations/ms-cliente.properties`
- `config-service/src/main/resources/configurations/ms-producto.properties`
- `config-service/src/main/resources/configurations/ms-ventas.properties`

## Ramas sugeridas

- `main`
- `develop`
- `feature/service-registry`
- `feature/config-service`
- `feature/api-gateway`
- `feature/ms-cliente`
- `feature/ms-producto`
- `feature/ms-ventas`

## Nota de base de datos

Credenciales en configuración son de ejemplo local. Ajusta usuario/clave/URL de MySQL, Oracle y PostgreSQL antes de producción.

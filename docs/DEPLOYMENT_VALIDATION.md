# Validación de Configuración y Despliegue

## Perfil Validado
- **dev**: Ejecutado localmente mediante Maven Wrapper.
- **test**: Evaluado de forma automatizada con JUnit y MockMvc.
- **prod**: Ejecutado de manera aislada dentro de un contenedor Docker.

## Endpoints Verificados
- `GET /deploy/config`
- `GET /deploy/health`
- `GET /deploy/checklist`
- `GET /deploy/version` (Ejercicio Aplicado)

## Evidencias Técnicas
- [ ] Ejecución exitosa de `./mvnw test` (BUILD SUCCESS).
- [ ] Salida de comandos `curl` locales y en contenedor.
- [ ] Estado activo verificado mediante `docker ps`.
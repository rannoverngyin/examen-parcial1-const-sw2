# Banco de pruebas – Sesión 13

## Módulo evaluado
ProductoService

## Casos de prueba

| ID | Caso de prueba | Prioridad | Resultado esperado |
|----|----------------|-----------|--------------------|
| PU-01 | Listar productos iniciales | Alta | Retorna Laptop y Mouse |
| PU-02 | Agregar producto válido | Alta | Incrementa total y producto existe |
| PU-03 | Eliminar producto existente | Media | Reduce total y producto ya no existe |
| PU-04 | Rechazar producto vacío | Alta | Lanza IllegalArgumentException |

## Comando de ejecución
./mvnw test

## Evidencia
### Build Success
![Build Success](docs/sesion13-build-success.png)

### Archivo de pruebas
![ProductoServiceTest](docs/sesion13-productoservicetest.png)

### Workflow GitHub Actions
![Workflow](docs/sesion13-workflow.png)

### Commit y Push en rama
![Commit Push](docs/sesion13-commit-push.png)

## Pull Request en GitHub
![Pull Request](docs\sesion13-pull-request.png)

### TEST_BANK
![TEST_BANK](docs/sesion13-testbank.png)





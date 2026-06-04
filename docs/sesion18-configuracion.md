# Sesión 18 - Configuración dinámica y perfiles de entorno

## Estudiante: Fabio UPIACHIHUA

## Perfiles implementados

| Perfil | Archivo | Características |
|--------|---------|-----------------|
| dev | application-dev.properties | Puerto 8080, mensaje desarrollo |
| test | application-test.properties | Puerto 8080, mensaje pruebas |
| prod | application-prod.properties | Puerto 8080, mensaje con variable de entorno |

## Endpoints creados

- `GET /config/entorno` - Devuelve el perfil activo
- `GET /config/mensaje` - Devuelve el mensaje configurado
- `GET /config/info` - Devuelve toda la configuración (entorno, mensaje, versión, soporte)
- `GET /config/soporte` - Devuelve el correo de soporte

## Validaciones realizadas

### Perfil DEV
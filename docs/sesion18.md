# Configuración dinámica y perfiles de entorno 

#### Crear archivos de configuración por entorno y editamos

*application.properties:*
``````````````````````````````````````````
spring.application.name=perfilesEntornos
server.port=8080
spring.profiles.active=dev
``````````````````````````````````````````
*application-dev.properties:*
````````````````````````````````````````
app.entorno=dev
app.mensaje=Entorno de desarrollo FIIS
app.version=1.0-DEV
````````````````````````````````````````
*application-test.properties:*
``````````````````````````````````````
app.entorno=test
app.mensaje=Entorno de pruebas FIIS
app.version=1.0-TEST
``````````````````````````````````````
*application-prod.properties:*
````````````````````````````````````````````````````````
app.entorno=prod
app.mensaje=${APP_MENSAJE:Entorno de producción FIIS}
app.version=1.0-PROD
````````````````````````````````````````````````````````

#### Creamos ConfiguracionService.java

``````````````````````````````````````````````````````````````
package configuracion.perfilesEntornos.application;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;


@Service
public class ConfiguracionService {

    @Value("${app.entorno}")
    private String entorno;

    @Value("${app.mensaje}")
    private String mensaje;

    @Value("${app.version}")
    private String version;

    public String ObtenerEntorno(){
        return entorno;
     }

     public String ObtenerMensaje(){
        return mensaje;
     }

     public String ObtenerVersion(){
        return version;
    }
}
``````````````````````````````````````````````````````````````

#### Creamos ConfiguracionController.java

``````````````````````````````````````````````````````````````````````````
package configuracion.perfilesEntornos.presentation;

import org.springframework.web.bind.annotation.*;
import configuracion.perfilesEntornos.application.ConfiguracionService;
import java.util.*;

@RestController
@RequestMapping("/config")
public class ConfiguracionController {
    private final ConfiguracionService configuracionService;
    
    public ConfiguracionController(ConfiguracionService configuracionService){
        this.configuracionService=configuracionService;
    }

    @GetMapping("/entorno")
    public String entorno(){
        return configuracionService.ObtenerEntorno();
    }

    @GetMapping("/mensaje")
    public String mensaje(){
        return configuracionService.ObtenerMensaje();
    }

@GetMapping("/info")
    public Map<String, String> info(){
        return Map.of(
            "entorno", configuracionService.ObtenerEntorno(),
            "mensaje", configuracionService.ObtenerMensaje(),
            "version", configuracionService.ObtenerVersion()
        );
    }
    
}
``````````````````````````````````````````````````````````````````````````

#### Ejecutamos y validamos perfiles



``````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````

[INFO] Attaching agents: []

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v3.5.0)

2026-06-04T10:00:17.743-05:00  INFO 40800 --- [perfilesEntornos] [  restartedMain] c.p.PerfilesEntornosApplication          : Starting PerfilesEntornosApplication using Java 21.0.10 with PID 40800 (E:\CURSOS\CONSTRUCCION DE SOFTWARE II\perfilesEntornos\target\classes started by USUARIO in E:\CURSOS\CONSTRUCCION DE SOFTWARE II\perfilesEntornos)
2026-06-04T10:00:17.745-05:00  INFO 40800 --- [perfilesEntornos] [  restartedMain] c.p.PerfilesEntornosApplication          : The following 1 profile is active: "dev"
2026-06-04T10:00:17.787-05:00  INFO 40800 --- [perfilesEntornos] [  restartedMain] .e.DevToolsPropertyDefaultsPostProcessor : Devtools property defaults active! Set 'spring.devtools.add-properties' to 'false' to disable
2026-06-04T10:00:17.787-05:00  INFO 40800 --- [perfilesEntornos] [  restartedMain] .e.DevToolsPropertyDefaultsPostProcessor : For additional web related logging consider setting the 'logging.level.web' property to 'DEBUG'
2026-06-04T10:00:19.072-05:00  INFO 40800 --- [perfilesEntornos] [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8080 (http)
2026-06-04T10:00:19.093-05:00  INFO 40800 --- [perfilesEntornos] [  restartedMain] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2026-06-04T10:00:19.095-05:00  INFO 40800 --- [perfilesEntornos] [  restartedMain] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.41]
2026-06-04T10:00:19.157-05:00  INFO 40800 --- [perfilesEntornos] [  restartedMain] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2026-06-04T10:00:19.159-05:00  INFO 40800 --- [perfilesEntornos] [  restartedMain] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1369 ms
2026-06-04T10:00:19.619-05:00  INFO 40800 --- [perfilesEntornos] [  restartedMain] o.s.b.d.a.OptionalLiveReloadServer       : LiveReload server is running on port 35729
2026-06-04T10:00:19.671-05:00  INFO 40800 --- [perfilesEntornos] [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
2026-06-04T10:00:19.684-05:00  INFO 40800 --- [perfilesEntornos] [  restartedMain] c.p.PerfilesEntornosApplication          : Started PerfilesEntornosApplication in 2.343 seconds (process running for 3.118)

``````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````

*Ejecucion con perfil dev:*
![alt text](image.png)

*Ejecucion con perfil test:*

![alt text](image-1.png)

*Ejecución con perfil prod y variable de entorno:*
![alt text](image-2.png)

FUNCIONA CORRECTAMENTE


### EJERCICIO APLICADO
#### agregar una configuración soporte

![alt text](image-3.png)

![alt text](image-4.png)

![alt text](image-5.png)


#### Modificamos el Service y Controller

![alt text](image-6.png)

![alt text](image-7.png)


### Probamos con los perfiles dev, test y prod

![alt text](<Captura de pantalla 2026-06-04 102517.png>)

![alt text](image-8.png)

![alt text](image-9.png)
# Varibilidad en un Software

#### Crear la configuración de variabilidad


``````````````````````````````````````
spring.application.name=variabilidad
server.port=8080
app.variante-cliente=PREMIUM
``````````````````````````````````````
>*La propiedad app.variante-cliente controlará el comportamiento del cálculo de precios sin modificar el código fuente*


#### Crear servicio de variabilidad

````````````````````````````````````````````````````````````````
package sesion17.variabilidad.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class precioService {
    @Value("${app.variante-cliente:BASICO}")
    private String varianteCliente;

        public double calcularPrecioFinal(double precio) {
        return switch (varianteCliente.toUpperCase()) {
            case "PREMIUM" -> precio * 0.90;
            case "VIP" -> precio * 0.80;
            default -> precio;
        };
    }

    public String obtenerVarianteActiva() {
        return varianteCliente;
    }       
    
}
````````````````````````````````````````````````````````````````
>*@Value lee la configuración externa. Si la propiedad no existe, se usa BASICO como valor por defecto*

#### Crear Controller REST

``````````````````````````````````````````````````````````````
package sesion17.variabilidad.presentation;

import sesion17.variabilidad.application.precioService;
import org.springframework.web.bind.annotation.*;

@RestController
public class precioController {

    private final precioService service;

    public precioController(precioService service) {
        this.service = service;
    }

    @GetMapping("/precio-final")
    public double calcular(@RequestParam double precio) {
        return service.calcularPrecioFinal(precio);
    }

    @GetMapping("/variante-activa")
    public String variante() {
        return service.obtenerVarianteActiva();
    }
}
``````````````````````````````````````````````````````````````


#### Ejecutar el sistema

````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````
[INFO] Attaching agents: []

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.0.5)

2026-06-02T14:58:37.131-05:00  INFO 48000 --- [  restartedMain] s.variabilidad.VariabilidadApplication   : Starting VariabilidadApplication using Java 21.0.10 with PID 48000 (E:\CURSOS\CONSTRUCCION DE SOFTWARE II\variabilidad\target\classes started by USUARIO in E:\CURSOS\CONSTRUCCION DE SOFTWARE II\variabilidad)
2026-06-02T14:58:37.133-05:00  INFO 48000 --- [  restartedMain] s.variabilidad.VariabilidadApplication   : No active profile set, falling back to 1 default profile: "default"
2026-06-02T14:58:37.171-05:00  INFO 48000 --- [  restartedMain] .e.DevToolsPropertyDefaultsPostProcessor : Devtools property defaults active! Set 'spring.devtools.add-properties' to 'false' to disable
2026-06-02T14:58:37.173-05:00  INFO 48000 --- [  restartedMain] .e.DevToolsPropertyDefaultsPostProcessor : For additional web related logging consider setting the 'logging.level.web' property to 'DEBUG'
2026-06-02T14:58:37.741-05:00  INFO 48000 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2026-06-02T14:58:37.751-05:00  INFO 48000 --- [  restartedMain] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2026-06-02T14:58:37.751-05:00  INFO 48000 --- [  restartedMain] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.7]
2026-06-02T14:58:37.796-05:00  INFO 48000 --- [  restartedMain] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2026-06-02T14:58:37.797-05:00  INFO 48000 --- [  restartedMain] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 623 ms
2026-06-02T14:58:37.996-05:00  INFO 48000 --- [  restartedMain] o.s.b.d.a.OptionalLiveReloadServer       : LiveReload server is running on port 35729
2026-06-02T14:58:38.017-05:00  INFO 48000 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2026-06-02T14:58:38.021-05:00  INFO 48000 --- [  restartedMain] s.variabilidad.VariabilidadApplication   : Started VariabilidadApplication in 1.137 seconds (process running for 1.548)
````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````

#### Validar la variabilidad

comprobamos el tipo de valor y precio creado:

![alt text](image.png)

Cambiamos el tipo de precio en application.properties:

![alt text](image-1.png)

Comprobamos el cambio:

![alt text](image-2.png)


#### Crear prueba unitaria del servicio

Reajustamos el Service:

![alt text](image-3.png)


Creamos el Test:
````````````````````````````````````````````````````````````````````````````

package sesion17.variabilidad;

import sesion17.variabilidad.application.precioService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class precioServiceTest {

    private final precioService service = new precioService();

    @Test
    void debeAplicarDescuentoPremium() {
        assertEquals(90.0, service.calcularPorVariante(100, "PREMIUM"));
    }

    @Test
    void debeAplicarDescuentoVip() {
        assertEquals(80.0, service.calcularPorVariante(100, "VIP"));
    }

    @Test
    void debeMantenerPrecioBasico() {
        assertEquals(100.0, service.calcularPorVariante(100, "BASICO"));
    }

    
}
````````````````````````````````````````````````````````````````````````````

Ejecutamos las pruebas:

``````````````````````````````````````````````````````````````````````````````````````````````````````````````````
[INFO] --- surefire:2.22.2:test (default-test) @ integracion ---
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running sesion17.variabilidad.precioServiceTest
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.06 s - in sesion17.variabilidad.precioServiceTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] 
[INFO] --- jacoco:0.8.12:report (report) @ integracion ---
[INFO] Loading execution data file E:\CURSOS\CONSTRUCCION DE SOFTWARE II\variabilidad\target\jacoco.exec
[INFO] Analyzed bundle 'integracion' with 3 classes
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  7.660 s
[INFO] Finished at: 2026-06-02T15:38:32-05:00
[INFO] ------------------------------------------------------------------------

``````````````````````````````````````````````````````````````````````````````````````````````````````````````````

Todo funciona correctamente


#### EXTRA - Agregar descuento para estudiantes

Modificamos el application properties:
![alt text](image-4.png)

Ahora en el Service:
![alt text](image-5.png)

Y Agregar el Test:
![alt text](image-6.png)

Ejecutamos y verficamos:

![alt text](image-7.png)

````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running sesion17.variabilidad.precioServiceTest
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.046 s - in sesion17.variabilidad.precioServiceTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] 
[INFO] --- jacoco:0.8.12:report (report) @ integracion ---
[INFO] Loading execution data file E:\CURSOS\CONSTRUCCION DE SOFTWARE II\variabilidad\target\jacoco.exec
[INFO] Analyzed bundle 'integracion' with 3 classes
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.168 s
[INFO] Finished at: 2026-06-02T15:55:48-05:00
[INFO] ------------------------------------------------------------------------
````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````
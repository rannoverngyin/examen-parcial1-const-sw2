## Preparando el código base
# --SERVICE
import org.springframework.stereotype.Service; 

import java.util.ArrayList; 

import java.util.List; 


@Service 

public class ProductoService { 

    private final List<String> productos = new ArrayList<>(); 

    public ProductoService() { 

        productos.add("Laptop"); 

        productos.add("Mouse"); 

    } 

    public List<String> listar() { 

        return productos; 

    } 


    public void agregar(String nombre) { 

        productos.add(nombre); 

    }
 

    public void eliminar(String nombre) { 

        productos.remove(nombre); 

    } 

    public int total() { 

        return productos.size(); 

    } 
}
# CONTROLLER
package integracion.rest.presentation;

import integracion.rest.application.ProductoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController 

@RequestMapping("/productos") 

public class ProductoController { 

    private final ProductoService service; 

    public ProductoController(ProductoService service) { 

        this.service = service; 
    } 

    @GetMapping 
    public List<String> listar() { 

        return service.listar(); 
    } 

    @PostMapping 
    public String agregar(@RequestParam String nombre) { 

        service.agregar(nombre);
        return "Producto agregado"; 
    } 

    @DeleteMapping 
    public String eliminar(@RequestParam String nombre) { 

        service.eliminar(nombre); 
        return "Producto eliminado"; 
    } 

    @GetMapping("/total") 
    public int total() { 

        return service.total(); 
    } 
} 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v3.4.5)

2026-05-19T13:34:19.295-05:00  INFO 21928 --- [rest] [  restartedMain] integracion.rest.RestApplication         : Starting RestApplication using Java 21.0.10 with PID 21928 (E:\CURSOS\CONSTRUCCION DE SOFTWARE II\rest\target\classes started by USUARIO in E:\CURSOS\CONSTRUCCION DE SOFTWARE II\rest)
2026-05-19T13:34:19.297-05:00  INFO 21928 --- [rest] [  restartedMain] integracion.rest.RestApplication         : No active profile set, falling back to 1 default profile: "default"
2026-05-19T13:34:19.339-05:00  INFO 21928 --- [rest] [  restartedMain] .e.DevToolsPropertyDefaultsPostProcessor : Devtools property defaults active! Set 'spring.devtools.add-properties' to 'false' to disable
2026-05-19T13:34:19.339-05:00  INFO 21928 --- [rest] [  restartedMain] .e.DevToolsPropertyDefaultsPostProcessor : For additional web related logging consider setting the 'logging.level.web' property to 'DEBUG'
2026-05-19T13:34:19.886-05:00  INFO 21928 --- [rest] [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8080 (http)
2026-05-19T13:34:19.898-05:00  INFO 21928 --- [rest] [  restartedMain] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2026-05-19T13:34:19.898-05:00  INFO 21928 --- [rest] [  restartedMain] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.40]
2026-05-19T13:34:19.927-05:00  INFO 21928 --- [rest] [  restartedMain] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2026-05-19T13:34:19.928-05:00  INFO 21928 --- [rest] [  restartedMain] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 587 ms
2026-05-19T13:34:20.189-05:00  INFO 21928 --- [rest] [  restartedMain] o.s.b.d.a.OptionalLiveReloadServer       : LiveReload server is running on port 35729
2026-05-19T13:34:20.231-05:00  INFO 21928 --- [rest] [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
2026-05-19T13:34:20.238-05:00  INFO 21928 --- [rest] [  restartedMain] integracion.rest.RestApplication         : Started RestApplication in 1.212 seconds (process running for 1.464)


## Creación de prueba de integración REST

package integracion.rest.presentation;

import org.junit.jupiter.api.Test; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; 
import org.springframework.boot.test.context.SpringBootTest; 
import org.springframework.test.annotation.DirtiesContext; 
import org.springframework.test.web.servlet.MockMvc; 

import static org.hamcrest.Matchers.hasItem; 
import static org.hamcrest.Matchers.not; 
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; 

@SpringBootTest 
@AutoConfigureMockMvc 
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) 
class ProductoControllerIntegrationTest { 
    @Autowired 
    private MockMvc mockMvc; 

    @Test 
    void listarProductos_debeRetornarStatus200YListaInicial() throws Exception { 

        mockMvc.perform(get("/productos")) 
                .andExpect(status().isOk()) 
                .andExpect(jsonPath("$[0]").value("Laptop")) 
                .andExpect(jsonPath("$[1]").value("Mouse")); 
    } 

    @Test 
    void agregarProducto_debeRetornarMensajeYActualizarLista() throws Exception { 

        mockMvc.perform(post("/productos").param("nombre", "Teclado")) 
                .andExpect(status().isOk()) 
                .andExpect(content().string("Producto agregado")); 

        mockMvc.perform(get("/productos")) 
                .andExpect(status().isOk()) 
                .andExpect(jsonPath("$", hasItem("Teclado"))); 
    } 

    @Test 
    void eliminarProducto_debeRetirarProductoDeLaLista() throws Exception { 

        mockMvc.perform(delete("/productos").param("nombre", "Mouse")) 
                .andExpect(status().isOk()) 
                .andExpect(content().string("Producto eliminado")); 

        mockMvc.perform(get("/productos")) 
                .andExpect(status().isOk()) 
                .andExpect(jsonPath("$", not(hasItem("Mouse")))); 
    } 
    @Test 
    void totalProductos_debeRetornarCantidadInicial() throws Exception { 
        mockMvc.perform(get("/productos/total")) 
                .andExpect(status().isOk()) 
                .andExpect(content().string("2")); 
    } 
} 

RESULTADO

[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 3.006 s -- in integracion.rest.presentation.ProductoControllerIntegrationTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  5.474 s
[INFO] Finished at: 2026-05-19T13:40:24-05:00
[INFO] ------------------------------------------------------------------------

## Agragar nuevo endpoint

# Actualizar el service
public boolean existe(String nombre) { 

    return productos.contains(nombre); 

} 
# actualizar el controller

@GetMapping("/existe") 
public boolean existe(@RequestParam String nombre) { 

    return service.existe(nombre); 
} 

# actualizar el test
    @Test 
void existeProducto_debeRetornarTrueCuandoExiste() throws Exception { 
    mockMvc.perform(get("/productos/existe").param("nombre", "Laptop")) 

            .andExpect(status().isOk()) 
            .andExpect(content().string("true")); 
} 

RESULTADO
.244 seconds (process running for 3.855)
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 3.436 s -- in integracion.rest.presentation.ProductoControllerIntegrationTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  5.398 s
[INFO] Finished at: 2026-05-19T13:45:57-05:00
[INFO] ------------------------------------------------------------------------
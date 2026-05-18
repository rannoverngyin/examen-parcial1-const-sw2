## Preparar el servicio para pruebas de concurrencia 

package prueba.integracion.application;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ProductoService {
    private final List<String> productos = new CopyOnWriteArrayList<>();
    public ProductoService(){
        productos.add("Laptop");
        productos.add("Mouse");
    }
    public List<String> listar(){
        return productos;
    }
    public void agregar(String nombre){
        productos.add(nombre);
    }
    public void eliminar(String nommbre){
        productos.remove(nommbre);
    }
    public int total(){
        return productos.size();
    }
}

## Crear Controller
package prueba.integracion.presentation;

import prueba.integracion.application.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<String> listarProductos() {
        return productoService.listar();
    }

    @PostMapping
    public String agregarProducto(@RequestParam String nombre) {
        productoService.agregar(nombre);
        return "Producto agregado";
    }
}


## Crear prueba de integración del Controller

package prueba.integracion;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
 class ProductoControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void ListarProductos_debeResponderOk(){
        ResponseEntity<String> response = restTemplate.getForEntity("/productos", String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("Laptop"));
    }

    @Test
    void agregarProducto_debeResponderMensajeCorrecto(){
        ResponseEntity<String> response = restTemplate.postForEntity("/productos?nombre=Teclado", null, String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Producto agregado", response.getBody());
    }
}


## Crear prueba de concurrencia del servicio

package prueba.integracion;

import prueba.integracion.application.ProductoService;
import org. junit.jupiter.api.Test;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;


 class ProductoServiceConcurrencyTest {

    @Test
    void agregarProductoConcurrentemente_debeMantenerConteoCorrecto() throws Exception{
        ProductoService service = new ProductoService();
        int inicial = service.total();
        int hilos = 10;

        ExecutorService executor = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(hilos);

        for (int i= 0; i < hilos; i++){
            int numero = i;
            executor.submit (() ->{
                service.agregar("Producto-" + numero);
                latch.countDown();
            });
        }
        boolean terminado = latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();

        assertTrue(terminado);
        assertEquals(inicial + hilos, service.total());

    }
    
}


##  prueba concurrente sobre la API

package prueba.integracion;

import org.junit.jupiter.api.Test; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.boot.test.context.SpringBootTest; 
import org.springframework.boot.test.web.client.TestRestTemplate; 
import org.springframework.http.ResponseEntity;

import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductoApiConcurrencyTest {

    @Autowired 
    private TestRestTemplate restTemplate;

    @Test 
    void multiplesPostConcurrentes_debenResponderCorrectamente() throws Exception { 
        int solicitudes = 10; 
        ExecutorService executor = Executors.newFixedThreadPool(5); 
        CountDownLatch latch = new CountDownLatch(solicitudes); 
 
        for (int i = 0; i < solicitudes; i++) { 
            int numero = i; 
            executor.submit(() -> { 
                ResponseEntity<String> response = restTemplate.postForEntity( 
                        "/productos?nombre=Concurrente-" + numero, 
                        null, 
                        String.class 
                ); 
                assertTrue(response.getStatusCode().is2xxSuccessful()); 
                latch.countDown(); 
            }); 
        } 
 
        boolean terminado = latch.await(5, TimeUnit.SECONDS); 
        executor.shutdown(); 
        assertTrue(terminado); 
    } 

    
}

## Ejecutar las pruebas 

[INFO] Results:
[INFO] 
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  7.397 s
[INFO] Finished at: 2026-05-18T17:40:26-05:00
[INFO] ------------------------------------------------------------------------
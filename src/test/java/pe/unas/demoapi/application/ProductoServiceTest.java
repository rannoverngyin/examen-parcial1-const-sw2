package pe.unas.demoapi.application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ProductoServiceTest {
    @Test 
    void debeAgregarProductosConcurrectemente() throws Exception {
        ProductoService service = new ProductoService();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
           int n=i; 
        executor.submit(() -> {
            service.agregar("Producto " + n);
        
        });

        
    }
    executor.shutdown();
    executor.awaitTermination(1, TimeUnit.MINUTES);
    assertEquals(10, service.listar().size());
}

}
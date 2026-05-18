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

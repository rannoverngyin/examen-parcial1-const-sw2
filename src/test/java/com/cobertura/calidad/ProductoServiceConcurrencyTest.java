package com.cobertura.calidad;

import org.junit.jupiter.api.Test;
import com.cobertura.calidad.application.ProductoService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ProductoServiceConcurrencyTest {

    @Test
    void agregarProductosConcurrentemente_debeMantenerConteoCorrecto() throws Exception {
        ProductoService service = new ProductoService();
        int inicial = service.total();
        int hilos = 20;

        ExecutorService executor = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(hilos);

        for (int i = 0; i < hilos; i++) {
            int numero = i;
            executor.submit(() -> {
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
package pe.unas.demoapi;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class ProductoApiConcurrencyTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void agregarProductosConcurrentemente_debeMantenerConteoCorrecto() throws Exception {
        Integer inicial = restTemplate.getForObject("/productos/total", Integer.class);
        assertNotNull(inicial);
        int hilos = 20;

        ExecutorService executor = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(hilos);

        for (int i = 0; i < hilos; i++) {
            int numero = i;
            executor.submit(() -> {
                try {
                    restTemplate.postForEntity(
                            "/productos?nombre=Producto-" + numero,
                            null,
                            String.class);
                } finally {
                    latch.countDown();
                }
            });
        }

        boolean terminado = latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();

        assertTrue(terminado);
        Integer total = restTemplate.getForObject("/productos/total", Integer.class);
        assertEquals(inicial + hilos, total);
    }
}

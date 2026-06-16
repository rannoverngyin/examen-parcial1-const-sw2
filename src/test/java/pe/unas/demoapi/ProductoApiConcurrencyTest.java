/*package pe.unas.demoapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductoApiConcurrencyTest {

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
*/
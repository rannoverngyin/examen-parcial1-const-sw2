package pe.unas.demoapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PresentationIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String endpoint(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void contextoCargaYEndpointAulasDevuelveListaEsperada() {
        ResponseEntity<List> response = restTemplate.getForEntity(endpoint("/aulas"), List.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).containsExactly("Aula 101", "Aula 102");
    }

    @Test
    void contextoCargaYEndpointInvestigadoresDevuelveListaEsperada() {
        ResponseEntity<List> response = restTemplate.getForEntity(endpoint("/investigadores"), List.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).containsExactly("Luis Gorpa", "Rannoverng Yanac");
    }

    @Test
    void contextoCargaYEndpointLineasDevuelveListaEsperada() {
        ResponseEntity<List> response = restTemplate.getForEntity(endpoint("/lineas"), List.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).containsExactly("Linea 1", "Linea 2", "Linea 3");
    }

    @Test
    void soportaSolicitudesConcurrentesAulas() throws Exception {
        assertConcurrentResponses("/aulas", List.of("Aula 101", "Aula 102"));
    }

    @Test
    void soportaSolicitudesConcurrentesInvestigadores() throws Exception {
        assertConcurrentResponses("/investigadores", List.of("Luis Gorpa", "Rannoverng Yanac"));
    }

    @Test
    void soportaSolicitudesConcurrentesLineas() throws Exception {
        assertConcurrentResponses("/lineas", List.of("Linea 1", "Linea 2", "Linea 3"));
    }

    private void assertConcurrentResponses(String path, List<String> expectedBody) throws Exception {
        int requestCount = 40;
        ExecutorService executor = Executors.newFixedThreadPool(10);

        try {
            List<Callable<ResponseEntity<List>>> tasks = IntStream.range(0, requestCount)
                    .mapToObj(i -> (Callable<ResponseEntity<List>>) () -> restTemplate.getForEntity(endpoint(path), List.class))
                    .collect(Collectors.toList());

            List<Future<ResponseEntity<List>>> futures = executor.invokeAll(tasks);

            for (Future<ResponseEntity<List>> future : futures) {
                ResponseEntity<List> response = future.get(10, TimeUnit.SECONDS);

                assertThat(response.getStatusCodeValue()).isEqualTo(200);
                assertThat(response.getBody()).containsExactlyElementsOf(expectedBody);
            }
        } finally {
            executor.shutdownNow();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}

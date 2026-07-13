package prueba27.cargas.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CargaService {

    private final AtomicLong contador = new AtomicLong();

    @Value("${app.carga.delay-ms:20}")
    private long delayMs;

    public List<String> listarProductos() {
        contador.incrementAndGet();
        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return List.of("Laptop", "Mouse", "Teclado", "Monitor");
    }

    public long totalPeticiones() {
        return contador.get();
    }
}

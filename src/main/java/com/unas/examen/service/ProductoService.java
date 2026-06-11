package com.unas.examen.service;

import com.unas.examen.model.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductoService {

    private final List<Producto> productos = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    public ProductoService() {
        // Datos de ejemplo
        productos.add(new Producto(counter.getAndIncrement(), "Cacao Fino", "Cacao fino de aroma de la selva peruana", 25.50, 100));
        productos.add(new Producto(counter.getAndIncrement(), "Chocolate Negro 70%", "Tableta artesanal 70% cacao", 12.00, 200));
        productos.add(new Producto(counter.getAndIncrement(), "Pasta de Cacao", "Pasta pura de cacao sin azucar", 18.75, 50));
        productos.add(new Producto(counter.getAndIncrement(), "Manteca de Cacao", "Manteca prensada en frio", 30.00, 30));
    }

    public List<Producto> findAll() {
        return new ArrayList<>(productos);
    }

    public Optional<Producto> findById(Long id) {
        return productos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public Producto save(Producto producto) {
        producto.setId(counter.getAndIncrement());
        productos.add(producto);
        return producto;
    }

    public Optional<Producto> update(Long id, Producto datos) {
        return findById(id).map(p -> {
            p.setNombre(datos.getNombre());
            p.setDescripcion(datos.getDescripcion());
            p.setPrecio(datos.getPrecio());
            p.setStock(datos.getStock());
            return p;
        });
    }

    public boolean delete(Long id) {
        return productos.removeIf(p -> p.getId().equals(id));
    }
}

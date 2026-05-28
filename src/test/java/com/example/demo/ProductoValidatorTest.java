package com.example.demo;




import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.example.demo.application.ProductoValidator;


public class ProductoValidatorTest {
    private final ProductoValidator validator = new ProductoValidator();

    


    @Test
    void deberechazarnombrevacio() {
        assertFalse(validator.esValido(""));
    }

    @Test
    void deberechazarnombrenulp() {
        assertFalse(validator.esValido(null));
    }

    @Test
    void debeaceptarnombrecontexto(){ 
    assertTrue(validator.esValido("Laptop"));
    }
}

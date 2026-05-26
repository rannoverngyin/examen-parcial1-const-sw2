package com.example.demos10;

import org.junit.jupiter.api.Test;
import com.example.demos10.application.EquipoService;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class EquipoServiceTest {
    @Test
    void debeListarDosEquipos(){
        EquipoService service = new EquipoService();
        assertEquals(2, service.listar().size()); 
        
    }
    
}

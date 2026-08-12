package com.gonzalo.api_cs2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.gonzalo.api_cs2.application.DiagnosticoTIService;

public class ActivosDiagnosticoTITest {
    @Test
    void debeContarObsoltetos(){
        DiagnosticoTIService service = new DiagnosticoTIService();
        long resultado = service.contarPorEstado("OBSOLETO");
        assertEquals(2, resultado);
    }
}

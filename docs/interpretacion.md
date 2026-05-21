Se agregó el método esAceptable(int porcentaje) en la clase CalidadService. 
Este método retorna true cuando la cobertura es mayor o igual a 70.

Se crearon pruebas unitarias para los valores solicitados: 70, 90 y 40. 
Los valores 70 y 90 retornan true, mientras que el valor 40 retorna false.

Al ejecutar ./mvnw clean test se obtuvo BUILD SUCCESS con 8 pruebas ejecutadas correctamente. 
Luego se revisó el reporte de JaCoCo, donde se verificó que el paquete pe.unas.demoapi.application alcanzó 88% de cobertura de instrucciones y 85% de cobertura de ramas.
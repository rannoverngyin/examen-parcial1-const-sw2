# CONSULTAS EFICIENTES A BASE DE DATOS MEDIANTE ORM VS SQL EN PYTHON

#### Creamos el entorno virtual
![alt text](<Captura de pantalla 2026-07-02 102942.png>)

#### Creamos la base de datos con los datos de prueba
![alt text](image.png)

![alt text](image-7.png)

####  Implementamos consulta ORM vs SQL directo

![alt text](image-2.png)

#### Ejecutamos
![alt text](image-3.png)

#### Analizamos el plan de consulta
![alt text](image-4.png)

#### ejecutamos
![alt text](image-5.png)

````````````````````````````````````````````````````````````````
El resultado suelta que hace uso de índice para búsqueda. Esto
ayuda a realizar una busqueda más rápido por no tiene que leer
todos las líneas de la tabla hasta encontrarlo sino con indicar,
en este caso el ciclo, busca directamente con el número señalado.
``````````````````````````````````````````````````````````````````

## RETO APLICADO: índice y comparacion

#### creamos el archivo del crear_indice.py
![alt text](image-6.png)

#### Revisamos resultados
![alt text](image-8.png)
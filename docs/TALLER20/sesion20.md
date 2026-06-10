# Contenedores y despliegue - Sesión 20

## Productos
- Imagen Docker
- Contenedor ejecutandose 

## Resultado
BUILD SUCCESS

## Evidencias
-	Captura de ./mvnw test exitoso.
-	Captura de ./mvnw clean package -DskipTests.
-	Captura de docker build exitoso.
-	Captura de docker ps mostrando el contenedor activo.
-	Captura de curl al endpoint funcionando.
-	Archivo Dockerfile y docker-compose.yml en el repositorio.
-	Commit y push en rama feature/apellido_nombre.


![Captura de TEST exitoso](TEST.png)
![Captura de CLEANPACKAGE exitoso](CLEANPACKAGE.png) 
![Captura de DOCKERBUILD exitoso](DOCKERBUILD.png) 
![Captura del contenido de DOCKERPS](DOCKERPS.png) 
![Captura del curl al endpoint /productos](DOCKERCOMPOSE.png)![](PRODUCTOS.png) 
![Captura de los archivos Dockerfile y docker-compose.yml](DOCKERFILE.png)![](DOCKERYML.png) 
![Captura de volumen persistente](VOLUMENPERSISTENTE.png) ![](VOLUMENSINELIMINAR.png)
![Evidencia del commit y push](COMMIT.png)![](PUSH.png) 

## Conclusión
Desplegar software no es solamente ejecutar codigo; es hacerlo reproducible, verificable y portable. Un contenedor permite que la aplicacion funcione igual en la PC del estudiante, en el laboratorio y en un servidor.
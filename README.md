# Back Law Office

## Descripción
Aplicación para la gestión de un despacho jurídico, desarrollada con Spring Boot.

## Requisitos previos
Antes de ejecutar la aplicación, asegúrate de tener instalados los siguientes componentes:
- [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) o superior.
- [Gradle](https://gradle.org/install/) (opcional, ya que el wrapper de Gradle está incluido).
- [MySQL](https://dev.mysql.com/downloads/) para la base de datos.

## Configuración inicial
1. Clona este repositorio en tu máquina local:
   ```bash
   git clone https://github.com/Davidrc26/back_law_office.git
   cd back_law_office

# Nombre del Proyecto

Proyecto académico para la gestión de casos en el consultorio jurídico de la Universidad de Caldas

## Tecnologías utilizadas

- Java 17
- Spring Boot (versión 3.4.4)
- Gradle

## Requisitos previos

Antes de ejecutar esta aplicación, asegúrate de tener instalado:

- [Java 17 JDK](https://adoptium.net/)
- [Gradle](https://gradle.org/install/) (o usar el wrapper incluido)
- Base de datos mySQL

## Clonar el repositorio

```bash
git clone https://github.com/Davidrc26/back_law_office.git
cd back_law_office
```

## Construcción y ejecución

### Usando el wrapper de Gradle

Para compilar el proyecto:

```bash
./gradlew build
```

Para ejecutar la aplicación:

```bash
./gradlew bootRun
```

O en sistemas windows

```bash
./gradlew.bat bootRun
```

### Usando Gradle instalado localmente

```bash
gradle build
gradle bootRun
```

## Configuración

Las variables de entorno y parámetros de configuración se encuentran en:

```
src/main/resources/application.properties
```

Puedes configurar cosas como la conexión a base de datos, el puerto del servidor, entre otros.



## Base de datos


```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tu_base_de_datos
spring.datasource.username=usuario
spring.datasource.password=contraseña
```



## Pruebas

Ejecuta las pruebas con:

```bash
./gradlew test
```

## Compilación del JAR

```bash
./gradlew bootJar
```

El JAR generado se encontrará en `build/libs/`.

Para ejecutarlo:

```bash
java -jar build/libs/nombre-del-jar.jar
```

## Autor

- David Ramirez Cruz(https://github.com/Davidrc26)

## Licencia

Este proyecto está licenciado bajo la licencia [MIT](LICENSE).

# Usa una imagen con Java 17, compatible con Spring Boot 3
FROM eclipse-temurin:17-jdk-alpine

# Crea un volumen temporal para logs y archivos
VOLUME /tmp

# Expone el puerto interno (opcional pero recomendado)
EXPOSE 8080

# Copia el .jar generado por Spring Boot (usa nombre exacto del .jar)
COPY target/CRUD-backend-0.0.1-SNAPSHOT.jar app.jar

# Comando para ejecutar tu aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]



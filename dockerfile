# Etapa de compilacion
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /build

# Cachear dependencias primero para que no se descarguen cada vez
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el codigo fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests -B

# Etapa de ejecucion (imagen mas ligera, solo JRE)
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

RUN mkdir -p /data

COPY --from=builder /build/target/gestion-tareas-*.jar app.jar

EXPOSE 8080

# Variables de entorno para la base de datos H2 en modo fichero
ENV SPRING_DATASOURCE_URL=jdbc:h2:file:/data/gestiontareasdb
ENV SPRING_DATASOURCE_USERNAME=sa
ENV SPRING_DATASOURCE_PASSWORD=
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update
ENV SPRING_H2_CONSOLE_ENABLED=true
ENV SPRING_H2_CONSOLE_SETTINGS_WEB_ALLOW_OTHERS=true

# Volumen para persistir la base de datos
VOLUME ["/data"]

ENTRYPOINT ["java", "-jar", "app.jar"]

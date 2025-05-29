# --- Etapa de Construcción (Builder) ---
# Usamos una imagen base con JDK 21 y Maven para compilar la aplicación
FROM eclipse-temurin:21-jdk-jammy as builder

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw package -DskipTests

# --- Etapa Final (Runtime) ---
# Usamos una imagen base con JRE 21
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

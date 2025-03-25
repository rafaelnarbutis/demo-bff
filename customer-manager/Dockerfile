# Etapa de build com Maven
FROM maven:3.8.4-openjdk-17-slim AS build

# Copiar código fonte e pom.xml
COPY src /app/src
COPY pom.xml /app

# Trabalhar no diretório /app
WORKDIR /app

# Rodar o Maven para compilar o projeto e gerar o arquivo JAR
RUN mvn clean install -DskipTests

# Etapa final com imagem do OpenJDK 17
FROM openjdk:17-jdk-slim

# Copiar o arquivo JAR gerado do contêiner de build
COPY --from=build /app/target/customer-manager-0.0.1-SNAPSHOT.jar /app/customer-manager.jar

# Trabalhar no diretório /app
WORKDIR /app

# Expor a porta 8080 para a aplicação
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "customer-manager.jar"]

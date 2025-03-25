# Etapa de build com Maven
FROM openjdk:21-slim AS build

# Instalar Maven manualmente
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Copiar código fonte e pom.xml
COPY src /app/src
COPY pom.xml /app

# Trabalhar no diretório /app
WORKDIR /app

# Rodar o Maven para compilar o projeto e gerar o arquivo JAR
RUN mvn clean install -DskipTests

# Etapa final com imagem do OpenJDK 21
FROM openjdk:21-slim

# Copiar o arquivo JAR gerado do contêiner de build
COPY --from=build /app/target/*.jar /app/store-bff.jar

# Trabalhar no diretório /app
WORKDIR /app

# Expor a porta 8080 para a aplicação
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "store-bff.jar"]
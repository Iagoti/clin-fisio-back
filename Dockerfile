# ========= BUILD =========
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# cache de dependências
COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline

# build do projeto
COPY src ./src
RUN mvn -q -DskipTests package

# ========= RUNTIME =========
FROM eclipse-temurin:21-jre
WORKDIR /app

# copia o jar gerado
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# opcional: melhora comportamento em container
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]

FROM eclipse-temurin:21-jdk-jammy

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

EXPOSE 8080

ENTRYPOINT ["java","-jar","target/Order_Service-0.0.1-SNAPSHOT.jar"]

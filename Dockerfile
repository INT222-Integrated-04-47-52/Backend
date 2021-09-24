FROM maven:3.6.1-jdk-11-slim AS backend
COPY src /workspace/src
COPY pom.xml /workspace
WORKDIR /workspace
RUN mvn clean install

FROM openjdk:11.0-slim
EXPOSE 5000
COPY --from=backend /workspace/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]

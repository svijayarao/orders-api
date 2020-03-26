FROM openjdk:8-jdk-alpine
ARG APPLICATION_JAR=target/orders-api.jar
COPY ${APPLICATION_JAR} /tmp/app.jar
ENTRYPOINT ["sh", "-c", "java -jar /tmp/app.jar"]
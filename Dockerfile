FROM openjdk:18-jdk-alpine3.15
MAINTAINER jdanranjo
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} trainingapp-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/trainingapp-0.0.1-SNAPSHOT.jar"]
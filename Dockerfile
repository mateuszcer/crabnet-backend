FROM openjdk:17-jdk-alpine
MAINTAINER mateuszcer
COPY build/libs/crabnet-1.0.0.jar crabnet-1.0.0.jar
ENTRYPOINT ["java","-jar","/crabnet-1.0.0.jar"]
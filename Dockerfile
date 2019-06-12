FROM openjdk:8-jdk-alpine
COPY ["build/libs/standalone-0.1.jar", "."]
ENTRYPOINT ["java","-jar","/standalone-0.1.jar"]

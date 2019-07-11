FROM java:8
EXPOSE 5555
COPY ["build/libs/standalone-0.1.jar", "."]
ENTRYPOINT ["java","-jar","/standalone-0.1.jar", "--server.port=5555"]

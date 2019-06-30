FROM java:8
COPY ["build/libs/standalone-0.1.jar", "."]
EXPOSE 5555
ENTRYPOINT ["java","-jar","/standalone-0.1.jar", "--server.port=5555"]

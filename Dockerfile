FROM java:8
EXPOSE 5555
COPY ["build/libs/standalone-0.3.jar", "."]
ENTRYPOINT ["java","-jar","/standalone-0.3.jar", "--server.port=5555"]

FROM java:8
EXPOSE 5555
ENTRYPOINT ["java","-jar","/standalone-0.1.jar", "--server.port=5555"]

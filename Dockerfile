FROM openjdk:17-oracle
COPY target/*.jar /app/app.jar
WORKDIR /app
EXPOSE 80
ENTRYPOINT ["java", "-jar", "app.jar"]
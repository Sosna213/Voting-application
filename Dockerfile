FROM openjdk:11
EXPOSE 8080
ADD target/voting-application-backend.jar voting-application-backend.jar
ENTRYPOINT ["java", "-jar", "voting-application-backend.jar"]

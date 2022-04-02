FROM openjdk:11

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080
EXPOSE 5005

CMD ["./mvnw", "spring-boot:run"]

ENTRYPOINT ["java","-jar","/app.jar"]
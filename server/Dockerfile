FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
CMD ["./mvnw", "spring-boot:run"]
ENTRYPOINT ["java","-jar","/app.jar"]
#docker run -it --rm -d -v mysql_data:/var/lib/mysql -v mysql_config:/etc/mysql/conf.d --network=bridge --name mysql-db -e MYSQL_USER=admin -e MYSQL_PASSWORD=admin -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=concessionaria -p 3306:3306 mysql:8.0.23
#docker container create --name concessionaria-app --network=db -p 8080:8080 java-docker
#docker build --tag=concessionaria:latest .
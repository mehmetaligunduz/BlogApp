FROM openjdk:21
LABEL authors="mehmetali.gunduz"
ARG JAR_FILE=build/libs/*.jar
ENV MYSQL_HOST=mysql-db
ENV MYSQL_PORT=3306
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root
COPY ./build/libs/BlogApp-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
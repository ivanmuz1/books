FROM openjdk:17
ARG JAR_FILE=build/libs/books-0.0.1-SNAPSHOT-plain.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]

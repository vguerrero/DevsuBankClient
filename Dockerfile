FROM openjdk:17-alpine
VOLUME /tmp
EXPOSE 8181
ARG JAR_FILE=target/bankclient-1.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
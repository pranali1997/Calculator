FROM openjdk:11-jdk


COPY build/libs/calculator-0.0.1-SNAPSHOT.jar calculator.jar
COPY ./config/application-dev.properties application-dev.properties
EXPOSE 8080

# CMD java -jar calculator.jar application.properties
ENTRYPOINT ["java", "-jar", "calculator.jar"]


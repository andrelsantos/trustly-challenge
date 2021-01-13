FROM openjdk

WORKDIR /app

COPY target/challenge-0.0.1-SNAPSHOT.jar /app/challenge-app.jar

ENTRYPOINT ["java", "-jar","challenge-app.jar"]
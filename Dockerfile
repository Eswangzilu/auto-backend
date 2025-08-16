FROM openjdk:26-slim-trixie

LABEL authors="ZILU WANG"

WORKDIR /app

COPY target/clock.jar clock.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "clock.jar"]
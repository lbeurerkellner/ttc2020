# syntax=docker/dockerfile:1

FROM gradle:7.0.2-jdk11

WORKDIR /emf-syncer_ttc2020
COPY . .
RUN ./gradlew clean compileJava compileTestJava
CMD ./gradlew test && cat results.csv
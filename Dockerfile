FROM gradle:jdk17 AS build
WORKDIR /app
COPY ./src /app/src
COPY ./build.gradle.kts /app/build.gradle.kts
COPY ./settings.gradle.kts /app/settings.gradle.kts
COPY ./gradlew /app/gradlew
RUN gradle build

FROM openjdk:17-alpine
EXPOSE 8080
COPY --from=build /app/build/libs/rock-paper-scissors-0.0.2-SNAPSHOT.jar /app/rock-paper-scissors.jar
ENTRYPOINT ["java", "-jar","/app/rock-paper-scissors.jar"]
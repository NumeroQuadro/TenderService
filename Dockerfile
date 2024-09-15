FROM gradle:8.10.1-jdk17-alpine AS build

WORKDIR /home/gradle/src

COPY --chown=gradle:gradle backend/RestAPI /home/gradle/src

RUN gradle clean build --no-daemon --stacktrace --info

FROM openjdk:17.0.1-jdk-slim

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar

ENTRYPOINT ["java","-jar","/app/spring-boot-application.jar"]

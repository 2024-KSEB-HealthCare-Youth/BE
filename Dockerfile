# doker 이미지 생성을 위한 스크립트

FROM openjdk:17
COPY build/libs/fmhj-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
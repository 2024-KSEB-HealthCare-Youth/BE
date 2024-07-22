# 기본 Java 이미지 사용
FROM openjdk:17

# JAR 파일 경로를 인자로 받음
ARG JAR_FILE=build/libs/app.jar

# 파일을 컨테이너의 app.jar로 복사
COPY ${JAR_FILE} /app.jar

# 컨테이너 시작 시 Java 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]

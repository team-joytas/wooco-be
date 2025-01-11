FROM amazoncorretto:17-alpine-jdk

WORKDIR /app
COPY ./build/libs/*.jar /app/wooco-be.jar

ENV TZ=Asia/Seoul

CMD ["java", "-jar", "-Djava.net.preferIPv4Stack=true", "wooco-be.jar"]

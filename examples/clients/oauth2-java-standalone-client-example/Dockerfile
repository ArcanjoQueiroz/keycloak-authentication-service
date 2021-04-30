FROM azul/zulu-openjdk-alpine:16-jre

RUN apk --no-cache add curl busybox-extras

ARG JAR_FILE

ADD target/${JAR_FILE} /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

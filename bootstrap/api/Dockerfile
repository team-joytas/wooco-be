#============================ STAGE: 커스텀 JRE 생성 ============================
FROM amazoncorretto:21-alpine3.21 AS jre-builder

RUN apk add --no-cache binutils

RUN rm -rf /opt/jre && \
    $JAVA_HOME/bin/jlink \
    --add-modules ALL-MODULE-PATH \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=zip-6 \
    --output /opt/jre && \
    /opt/jre/bin/java -Xshare:dump

#============================ STAGE: JAR 추출 및 CDS 아카이브 생성 ============================
FROM alpine:3.21 AS extractor

WORKDIR /extract

COPY --from=jre-builder /opt/jre /opt/jre
ENV JAVA_HOME=/opt/jre
ENV PATH="$JAVA_HOME/bin:$PATH"
ENV TZ=Asia/Seoul

COPY ./build/libs/api-*.jar /extract/app.jar

# Spring Boot JAR 추출
RUN java -Djarmode=tools -jar /extract/app.jar extract --destination application

# CDS 아카이브 생성 (외부 의존성 연결 우회)
RUN cd application && \
    java \
    -XX:ArchiveClassesAtExit=application.jsa \
    -Dspring.context.exit=onRefresh \
    -Dspring.flyway.enabled=false \
    -Dspring.sql.init.mode=never \
    -Dspring.jpa.hibernate.ddl-auto=none \
    -Dspring.jpa.database-platform=org.hibernate.dialect.MySQLDialect \
    -Dspring.datasource.hikari.minimum-idle=0 \
    -Dspring.datasource.hikari.maximum-pool-size=1 \
    -Dspring.datasource.hikari.initialization-fail-timeout=-1 \
    -Dspring.datasource.hikari.connection-timeout=1000 \
    -Dspring.data.redis.timeout=1ms \
    -jar app.jar || true

#============================ STAGE: 런타임 이미지 ============================
FROM alpine:3.21

RUN addgroup -g 1000 appuser \
    && adduser -D -u 1000 -G appuser -H -s /sbin/nologin appuser

COPY --from=jre-builder /opt/jre /opt/jre

WORKDIR /app

COPY --chown=appuser:appuser --from=extractor /extract/application/ ./

USER appuser:appuser

ENV JAVA_HOME=/opt/jre
ENV PATH="$JAVA_HOME/bin:${PATH}"
ENV TZ=Asia/Seoul

ENV JAVA_TOOL_OPTIONS="\
  -XX:SharedArchiveFile=/app/application.jsa \
  -Duser.timezone=Asia/Seoul \
  -Djava.io.tmpdir=/tmp \
  -Djava.net.preferIPv4Stack=true \
  -Dspring.aot.enabled=true"


ENTRYPOINT ["sh", "-c", "exec java ${JAVA_OPTS:-} -jar /app/app.jar \"$@\"", "--"]

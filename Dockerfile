FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /build

COPY pom.xml ./
RUN mvn -B -q dependency:go-offline

COPY src ./src
RUN mvn -B -q package


FROM eclipse-temurin:21-jre-alpine AS runtime

RUN addgroup -S app && adduser -S -G app app

WORKDIR /app

COPY --from=build --chown=app:app /build/target/app.jar ./app.jar

ARG VERSION=local
ARG COMMIT=unknown

ENV APP_VERSION=$VERSION \
    APP_COMMIT=$COMMIT \
    JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75"

USER app

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

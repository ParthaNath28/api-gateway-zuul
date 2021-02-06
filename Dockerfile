FROM openjdk:11.0.7-jre-slim

COPY target/api-gateway-zuul-0.0.1-SNAPSHOT.jar Apigateway.jar

ENTRYPOINT exec java $JAVA_OPTS -jar Apigateway.jar





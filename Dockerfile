FROM openjdk:11 as build
ADD ./target/BillingSystem-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT exec java $JAVA_OPTS -jar /app/BillingSystem-0.0.1-SNAPSHOT.jar

EXPOSE 9550
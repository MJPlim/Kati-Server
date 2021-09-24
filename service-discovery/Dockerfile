FROM openjdk:11
VOLUME /tmp
COPY target/service-discovery-0.0.1-SNAPSHOT.jar service-discovery.jar
ENTRYPOINT ["java", "-jar", "service-discovery.jar"]
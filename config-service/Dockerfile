FROM openjdk:11
VOLUME /tmp
COPY target/config-service-0.0.1-SNAPSHOT.jar kati-config-service.jar
ENTRYPOINT ["java", "-jar", "kati-config-service.jar"]
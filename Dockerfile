FROM openjdk:11-jdk-slim
VOLUME /tmp
ADD /target/sweetDreams-0.0.1-SNAPSHOT.jar sweetdreams.jar
EXPOSE 8080
RUN bash -c 'touch /sweetdreams.jar'
ENTRYPOINT ["java", "-jar", "/sweetdreams.jar"]


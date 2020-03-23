FROM openjdk:8-jre
MAINTAINER dk dk@lanlanzy.com

COPY target/lanmv-0.0.1-SNAPSHOT.jar /lanmv.jar
ENTRYPOINT ["java", "-jar", "/lanmv.jar"]

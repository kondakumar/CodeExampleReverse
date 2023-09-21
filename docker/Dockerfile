#Stage1
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package
FROM docker.wdf.sap.corp:51277/build-agent/sapmachine:17.0.6-1 as JarBuilder
COPY --from=build /home/app/target/CodeExampleReverse-0.0.1-SNAPSHOT.jar /usr/local/lib/reversedemo.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/usr/local/lib/reversedemo.jar"]
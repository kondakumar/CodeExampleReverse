#Stage1
FROM maven:3.8.5-openjdk-17-slim  AS build

COPY /Users/i342774/Desktop/CodeExampleReverse/src /usr/local/lib
COPY /Users/i342774/Desktop/CodeExampleReverse/pom.xml /usr/local/lib

WORKDIR /usr/local/lib

RUN mvn -f ./pom.xml clean package

FROM docker.wdf.sap.corp:51277/build-agent/sapmachine:17.0.6-1 as JarBuilder

COPY --from=build /usr/local/lib/target/CodeExampleReverse-0.0.1-SNAPSHOT.jar  /usr/local/lib/target/Codereversedemo.jar
EXPOSE 8080
RUN chmod -R 0777 "/usr/local/lib/target/reversedemo.jar"
ENTRYPOINT ["java","-jar","/usr/local/lib/target/Codereversedemo.jar"]

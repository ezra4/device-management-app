FROM openjdk:latest
ADD . ./DeviceManagement
WORKDIR /DeviceManagement
ARG JAR_FILE=*.jar
COPY ./target/DeviceManagement-0.0.1-SNAPSHOT.jar ./DeviceManagement-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","DeviceManagement-0.0.1-SNAPSHOT.jar"]

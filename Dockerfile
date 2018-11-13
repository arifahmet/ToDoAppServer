FROM openjdk:8-jdk-alpine
LABEL maintainer="arifahmetbarbak@gmail.com"
VOLUME /tmp
EXPOSE 5000
ADD target/backenddemo-0.0.1-SNAPSHOT.jar /
CMD java -jar backenddemo-0.0.1-SNAPSHOT.jar
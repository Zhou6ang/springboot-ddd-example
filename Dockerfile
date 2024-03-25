FROM openjdk:17-alpine

# Use alpine download jdk17, size: 280M vs 386M
#FROM alpine:3.19
#RUN apk add openjdk17-jre


# Install required packages
RUN apk update \
    && apk upgrade \
    && apk add --update  curl unzip bash
	


# Set working directory
WORKDIR /app

COPY target/*.jar /app/app.jar

ENV MY_ENV=xxx

EXPOSE 8888

CMD java -jar /app/app.jar --server.port=8888

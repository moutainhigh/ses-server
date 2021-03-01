FROM openjdk:8-jre-alpine

MAINTAINER jerry@redescooter.com

ENV TZ=UTC
ENV JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /ses-web-website

WORKDIR /ses-web-website

EXPOSE 8900

ADD ./target/libs/ses-web-website.jar ./

CMD sleep 30;java $JAVA_OPTS -jar ses-web-website.jar



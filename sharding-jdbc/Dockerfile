FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER chenqingsong@zimonet.com

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /sharding-jdbc

WORKDIR /sharding-jdbc

EXPOSE 8085

ADD ./target/app.jar ./

FROM alpine:latest

WORKDIR /usr/local/bin

RUN apk update && apk add curl busybox-extras jq apache2-utils

COPY . .

ENV WAIT_VERSION 2.8.0
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/$WAIT_VERSION/wait /wait
RUN chmod +x /wait

CMD ["/bin/sh"]

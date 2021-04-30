FROM golang:alpine AS build

WORKDIR /src/
COPY . /src/
RUN CGO_ENABLED=0 go build -o /bin/app

FROM alpine
EXPOSE 9089
RUN apk update && apk add curl busybox-extras

ENV WAIT_VERSION 2.8.0
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/$WAIT_VERSION/wait /wait
RUN chmod +x /wait

COPY --from=build /bin/app /bin/app
CMD ["/bin/app"]

#!/usr/bin/make -f
clean:
	rm app.linux-amd64
lint:
	find . -type f -name '*.go' -not -path "vendor*" -exec gofmt -w {} \;	
build:
	GOOS=linux GOARCH=amd64 CGO_ENABLED=0 go build -o app.linux-amd64
run:
	go run *.go
docker:
	docker build -t arcanjoqueiroz/oauth2-go-client-example:1.0.0 .
	

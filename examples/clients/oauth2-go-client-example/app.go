package main

import (
	"golang.org/x/net/context"
	"golang.org/x/oauth2/clientcredentials"
)

var config = clientcredentials.Config{
	ClientID:     "service-client-id",
	ClientSecret: "3f8fa682-041e-4f41-a263-025b813fb219",
	Scopes:       []string{"profile"},
	TokenURL:     "http://localhost:9999/auth/realms/master/protocol/openid-connect/token",
}

func main() {
	client := config.Client(context.Background())

	resp, err := client.Get("http://localhost:9091/consume")
	if err != nil {
		panic(err)
	} else {
		PrintResponse(resp)
	}

	resp, err = client.Get("http://localhost:9091/hi")
	if err != nil {
		panic(err)
	} else {
		PrintResponse(resp)
	}
}

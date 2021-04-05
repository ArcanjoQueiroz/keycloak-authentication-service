package main

import (
	"golang.org/x/net/context"
	"golang.org/x/oauth2/clientcredentials"
	"log"
	"os"
)

var config = clientcredentials.Config{
	ClientID:     "service-client-id",
	ClientSecret: os.Getenv("CLIENT_SECRET"),
	Scopes:       []string{"profile"},
	TokenURL:     "http://localhost:9999/auth/realms/master/protocol/openid-connect/token",
}

func main() {
	log.Printf("ClientId: %s", config.ClientID)
	log.Printf("Client Secret: %s", config.ClientSecret)
	log.Printf("Token URL: %s", config.TokenURL)
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

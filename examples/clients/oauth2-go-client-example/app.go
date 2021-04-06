package main

import (
	"golang.org/x/net/context"
	"golang.org/x/oauth2/clientcredentials"
	"log"
	"os"
)

var config = clientcredentials.Config{
	ClientID:     getEnv("CLIENT_ID", "service-client-id"),
	ClientSecret: getEnv("CLIENT_SECRET", "3f8fa682-041e-4f41-a263-025b813fb219"),
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

func getEnv(key, fallback string) string {
    value, exists := os.LookupEnv(key)
    if !exists {
        value = fallback
    }
    return value
}
package main

import (
	"log"
	"net/http"
	"os"

	"golang.org/x/net/context"
	"golang.org/x/oauth2"
	"golang.org/x/oauth2/clientcredentials"
)

var config = clientcredentials.Config{
	ClientID:     getEnv("CLIENT_ID", "test"),
	ClientSecret: getEnv("CLIENT_SECRET", "08d355bb-463d-4d18-b69e-a1ec1cb7ed11"),
	Scopes:       []string{"profile"},
	TokenURL:     getEnv("ACCESS_TOKEN_URI", "http://localhost:9999/auth/realms/master/protocol/openid-connect/token"),
}

var passwordConfig = oauth2.Config{
	ClientID:     getEnv("CLIENT_ID", "test"),
	ClientSecret: getEnv("CLIENT_SECRET", "08d355bb-463d-4d18-b69e-a1ec1cb7ed11"),
	Scopes:       []string{"profile"},
	Endpoint: oauth2.Endpoint{
		TokenURL:     getEnv("ACCESS_TOKEN_URI", "http://localhost:9999/auth/realms/master/protocol/openid-connect/token"),
	},
}

func main() {
	log.Printf("ClientId: %s", config.ClientID)
	log.Printf("Client Secret: %s", config.ClientSecret)
	log.Printf("Token URL: %s", config.TokenURL)

	ctx := context.Background()

	client := config.Client(ctx)

	token, err := passwordConfig.PasswordCredentialsToken(ctx, "alexandre", "foo")
	if err != nil {
		panic(err)
	} else {
		log.Printf("Access Token: %s", token.AccessToken)
	}	
	passwordClient := passwordConfig.Client(ctx, token)

	request(client)
	request(passwordClient)
}

func request(client *http.Client) {
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
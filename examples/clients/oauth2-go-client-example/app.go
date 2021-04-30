package main

import (
	"log"
	"net/http"
	"os"
	"fmt"
	"golang.org/x/net/context"
	"golang.org/x/oauth2"
	"golang.org/x/oauth2/clientcredentials"
)

var config = clientcredentials.Config{
	ClientID:     getEnv("CLIENT_ID", "test"),
	ClientSecret: getEnv("CLIENT_SECRET", "a167e1f1-870d-4926-89d8-738a8d214817"),
	Scopes:       []string{"profile"},
	TokenURL:     getEnv("ACCESS_TOKEN_URI", "http://localhost:9999/auth/realms/test/protocol/openid-connect/token"),
}

var passwordConfig = oauth2.Config{
	ClientID:     getEnv("CLIENT_ID", "test"),
	ClientSecret: getEnv("CLIENT_SECRET", "a167e1f1-870d-4926-89d8-738a8d214817"),
	Scopes:       []string{"profile"},
	Endpoint: oauth2.Endpoint{
		TokenURL:     getEnv("ACCESS_TOKEN_URI", "http://localhost:9999/auth/realms/test/protocol/openid-connect/token"),
	},
}

func main() {
	log.Printf("ClientId: %s", config.ClientID)
	log.Printf("Client Secret: %s", config.ClientSecret)
	log.Printf("Token URL: %s", config.TokenURL)

	ctx := context.Background()

	client := config.Client(ctx)

	username := getEnv("USERNAME", "alexandre")

	password := getEnv("PASSWORD", "foo")

	token, err := passwordConfig.PasswordCredentialsToken(ctx, username, password)
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
	baseurl := getEnv("SERVICE_BASE_URL", "http://localhost:9091")

	resp, err := client.Get(fmt.Sprintf("%s/consume", baseurl))
	if err != nil {
		panic(err)
	} else {
		PrintResponse(resp)
	}

	resp, err = client.Get(fmt.Sprintf("%s/hi", baseurl))
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

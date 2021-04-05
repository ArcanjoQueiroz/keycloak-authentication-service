package main

import (
	"fmt"
	"io/ioutil"
	"net/http"
)

// PrintResponse for print the HTTP Response
func PrintResponse(resp *http.Response) {
	body, _ := ioutil.ReadAll(resp.Body)
	defer resp.Body.Close()
	if resp.StatusCode == 200 {
		fmt.Println(string(body))
	} else {
		fmt.Println(string(body))
	}
}

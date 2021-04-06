#!/bin/bash
if ! [ -x "$(command -v jq)" ]; then
    sudo apt-get install jq -y
fi    

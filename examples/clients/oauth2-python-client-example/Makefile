#!/usr/bin/make -f
install:
	pip3.8 install -r requirements.txt
venv:
	python3.8 -m venv .venv
run:
	python3.8 app.py
docker:
	docker build -t arcanjoqueiroz/oauth2-python-client-example:1.0.0 .
	

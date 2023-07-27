#!/bin/bash
docker build -t my-mysql .
docker run -d -p 3306:3306 --name mysql my-mysql

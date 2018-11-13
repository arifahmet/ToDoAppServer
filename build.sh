#!/bin/sh
mvn clean install
docker build --rm -t appserver .
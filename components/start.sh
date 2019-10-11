#!/bin/bash
cd /Users/vicchan/CodeMonkey/MyProjects/spring-cloud-sample/components/docker-db/
docker-compose down
docker-compose up -d --build
cd /Users/vicchan/CodeMonkey/MyProjects/spring-cloud-sample/components/docker-kafka/
docker-compose down
docker-compose up -d --build
cd /Users/vicchan/CodeMonkey/MyProjects/spring-cloud-sample/components/docker-rabbitmq/
docker-compose down
docker-compose up -d --build
cd /Users/vicchan/CodeMonkey/MyProjects/spring-cloud-sample/components/docker-zipkin/
docker-compose down
docker-compose up -d --build
cd /Users/vicchan/CodeMonkey/MyProjects/spring-cloud-sample/components/docker-elk/
docker-compose down
docker-compose up -d --build
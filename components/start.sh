#!/bin/bash
cd /Users/vicchan/CodeMonkey/MyProjects/spring-cloud-sample/components/docker-db/
docker-compose up -d --build
cd /Users/vicchan/CodeMonkey/MyProjects/spring-cloud-sample/components/docker-kafka/
docker-compose up -d --build
cd /Users/vicchan/CodeMonkey/MyProjects/spring-cloud-sample/components/docker-rabbitmq/
docker-compose up -d --build
cd /Users/vicchan/CodeMonkey/MyProjects/spring-cloud-sample/components/docker-zipkin/
docker-compose up -d --build
cd /Users/vicchan/CodeMonkey/MyProjects/spring-cloud-sample/components/docker-elk/
docker-compose up -d --build
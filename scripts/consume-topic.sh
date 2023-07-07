#!/bin/bash

TOPIC_NAME=${1:-'flightLogistic'}; # default topic name

padrao="\033[0m"
ciano="\033[0;36m"

clear;
source .env && echo -e "\n ${padrao} Consuming topic: ${ciano}${TOPIC_NAME} ${padrao} \n";
kafka-console-consumer --bootstrap-server=localhost:9092 --topic=$TOPIC_NAME --property "parse.key=true" --property "key.separator=:" --consumer.config=src/main/resources/consumer.properties

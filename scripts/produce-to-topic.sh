#!/bin/bash

TOPIC_NAME=${1:-'flightLogistic'}; # default topic name

padrao="\033[0m"
ciano="\033[0;36m"

clear;
echo -e "\n ${padrao} Producing to topic: ${ciano}${TOPIC_NAME} ${padrao} \n";
kafka-console-producer --bootstrap-server=localhost:9092 --topic=$TOPIC_NAME --property "parse.key=true" --property "key.separator=:" --producer.config=src/main/resources/producer.properties

#!/bin/bash

FLIGHT_LOGISTIC_TOPIC=${FLIGHT_LOGISTIC_TOPIC:-'flightLogistic'}; # default topic name

padrao="\033[0m"
ciano="\033[0;36m"

clear;
echo -e "\n ${padrao} Consuming topic: ${ciano}${FLIGHT_LOGISTIC_TOPIC} ${padrao} \n";
kafka-console-consumer --bootstrap-server=localhost:9092 --topic=$FLIGHT_LOGISTIC_TOPIC --group=G1

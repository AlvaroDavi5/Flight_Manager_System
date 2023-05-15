#!/bin/sh

FLIGHT_NOTIFICATIONS_TOPIC=${FLIGHT_NOTIFICATIONS_TOPIC:-'flightNotifications'}; # default topic name

padrao="\033[0m"
ciano="\033[0;36m"

clear;
echo -e "\n ${padrao} Consuming topic: ${ciano}${FLIGHT_NOTIFICATIONS_TOPIC} ${padrao} \n";
kafka-console-consumer --bootstrap-server=localhost:9092 --topic=$FLIGHT_NOTIFICATIONS_TOPIC --group=G1

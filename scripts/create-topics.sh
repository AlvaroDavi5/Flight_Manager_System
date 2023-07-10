#!/bin/bash

padrao="\033[0m"
roxo="\033[0;35m"

source .env;
TOWER_REPORTS_TOPIC=${TOWER_REPORTS_TOPIC:-'towerReports'}
FLIGHT_LOGISTIC_TOPIC=${FLIGHT_LOGISTIC_TOPIC:-'flightLogistic'}
FLIGHT_NOTIFICATIONS_TOPIC=${FLIGHT_NOTIFICATIONS_TOPIC:-'flightNotifications'}
FLIGHT_MONITORING_TOPIC=${FLIGHT_MONITORING_TOPIC:-'flightMonitoring'}

clear;
echo -e "\n ${roxo} Creating Kafka Topics... ${padrao} \n";
kafka-topics --bootstrap-server=localhost:9092 --create --topic=$TOWER_REPORTS_TOPIC --partitions=3 --replication-factor=1;
kafka-topics --bootstrap-server=localhost:9092 --create --topic=$FLIGHT_LOGISTIC_TOPIC --partitions=3 --replication-factor=1;
kafka-topics --bootstrap-server=localhost:9092 --create --topic=$FLIGHT_NOTIFICATIONS_TOPIC --partitions=3 --replication-factor=1;
kafka-topics --bootstrap-server=localhost:9092 --create --topic=$FLIGHT_MONITORING_TOPIC --partitions=3 --replication-factor=1;

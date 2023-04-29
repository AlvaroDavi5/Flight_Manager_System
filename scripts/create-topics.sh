#!/bin/bash

padrao="\033[0m"
roxo="\033[0;35m"

clear;
echo -e "\n ${roxo} Creating Kafka Topics... ${padrao} \n";
kafka-topics --bootstrap-server=localhost:9092 --create --topic=flightLogistic --partitions=3 --replication-factor=1;
kafka-topics --bootstrap-server=localhost:9092 --create --topic=flightNotifications --partitions=3 --replication-factor=1;
kafka-topics --bootstrap-server=localhost:9092 --create --topic=towerReports --partitions=3 --replication-factor=1;
kafka-topics --bootstrap-server=localhost:9092 --create --topic=airTraffic --partitions=3 --replication-factor=1;

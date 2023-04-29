#!/bin/zsh

echo "Creating Kafka Topics...";
kafka-topics --bootstrap-server=localhost:9092 --create --topic=flightLogistic --partitions=3 --replication-factor=1;
kafka-topics --bootstrap-server=localhost:9092 --create --topic=flightNotifications --partitions=3 --replication-factor=1;
kafka-topics --bootstrap-server=localhost:9092 --create --topic=towerReports --partitions=3 --replication-factor=1;
kafka-topics --bootstrap-server=localhost:9092 --create --topic=airTraffic --partitions=3 --replication-factor=1;

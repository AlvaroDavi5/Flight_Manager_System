#!/bin/zsh

echo "Creating Kafka Topic...";
kafka-topics --bootstrap-server=localhost:9092 --create --topic=topic01 --partitions=3 --replication-factor=1;

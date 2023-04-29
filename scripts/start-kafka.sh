#!/bin/bash


padrao="\033[0m"
verde="\033[0;32m"

echo -e "\n ${verde} Starting Kafka... ${padrao} \n"
kafka-server-start src/main/java/configs/server.properties;

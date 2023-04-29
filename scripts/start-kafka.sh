#!/bin/bash

padrao="\033[0m"
verde="\033[0;32m"

clear;
echo -e "\n ${verde} Starting Kafka... ${padrao} \n";
kafka-server-start ./configs/server.properties;

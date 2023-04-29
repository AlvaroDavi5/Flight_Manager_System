#!/bin/bash


padrao="\033[0m"
amarelo="\033[1;33m"

echo -e "\n ${amarelo} Starting ZooKeeper... ${padrao} \n"
zookeeper-server-start src/main/java/configs/zookeeper.properties;

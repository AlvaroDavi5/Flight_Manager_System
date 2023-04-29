#!/bin/bash

padrao="\033[0m"
amarelo="\033[1;33m"

clear;
echo -e "\n ${amarelo} Starting ZooKeeper... ${padrao} \n";
zookeeper-server-start ./configs/zookeeper.properties;

#!/bin/bash

clear;
source .env && mvn clean compile exec:java -Dexec.args="./src/main/java/configs/producer.properties ./src/main/java/configs/consumer.properties"

#!/bin/bash

clear;
source .env && mvn clean compile exec:java -Dexec.args="./src/main/resources/producer.properties ./src/main/resources/consumer.properties";

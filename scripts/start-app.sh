#!/bin/zsh

clear && mvn clean compile exec:java -Dexec.args="./configs/producer.properties ./configs/consumer.properties"

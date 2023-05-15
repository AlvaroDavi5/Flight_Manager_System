#!/bin/bash

clear;
source .env && mvn clean compile exec:java -Dexec.args="SBGR GRU 200";
# mvn clean compile spring-boot:run -Dspring-boot.run.arguments="SBGR GRU 200";

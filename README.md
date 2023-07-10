
# Event-Oriented Monitoring System Applied to Flights

by _Álvaro Davi S. Alves_ - **2020101874**  

#### Computer Engineering :computer:
First assignment for the discipline  ```Tópicos Especiais em Informática IV (Event-Oriented Programming)```.  

### Universidade Federal do Espirito Santo ([UFES](https://ufes.br))

<img src="./docs/img/marca_ufes.png" alt="ufes logo" height="150px" width="250px">  

---


## Architecture and Backing Services

[Back-End Architecture](https://www.figma.com/file/DLgbATt7o29ccL3Qz8Gc2r/Flight-Manager-Architecture)  
[OpenSky API](https://openskynetwork.github.io/opensky-api/index.html)  

## Main technologies

- Java: programming laguage;
- JDK: Java Development Kit (compiler, runtime, VM...);
- Maven: Java project manager;
- Spring Framework: Multi-task framework that can be used for:
	* Boot: Spring initial setup;
	* Web: HTTP server creation and consume;
- JPA: SQL and NoSQL databases management by abstraction;
- PostgreSQL: Relational database;
- Log4J: Custom logger with appenders;
- Junit: Testing framework;
- Docker: Services isolation and process resources management with containers;

## How to run the project

To run this project, is recomended to use [JDK version 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or higher installed and these libraries: [Apache Kafka 3.4.0](https://downloads.apache.org/kafka/3.4.0/RELEASE_NOTES.html) and [Apache Maven 3.5.0](https://maven.apache.org/docs/3.5.0/release-notes.html).  
This project has created using:   
```sh
# create Maven project
$ mvn archetype:generate -DgroupId=com.flightmanager -DartifactId=Flight_Manager_System -DarchetypeVersion=1.4 -DinteractiveMode=false
```

After installing the JDK, you can run the project by typing the following commands in terminal:  
```sh
# load dotenv file
$ cp ./envs/.env.development.local ./.env && source .env
# start docker containers
$ docker-compose up -d
# install dependencies
$ mvn install
# reinstall dependencies
$ mvn dependency:purge-local-repository
# recompile project
$ mvn clean compile
# run project
$ mvn exec:java -Dexec.args="arg1 arg2"
# run tests
$ mvn test
# create JAR file
$ mvn package
```

---

## How to run the Apache Kafka
```sh
$ zookeeper-server-start configs/zookeeper.properties # start Zookeeper server manager
$ kafka-server-start configs/server.properties # start Kafka server
$ kafka-topics --bootstrap-server=localhost:9092 --create --topic=topic01 --partitions=3 --replication-factor=1 # create Kafka topic
$ kafka-console-producer --bootstrap-server=localhost:9092 --topic=topic01 --property="parse.key=true" --property="key.separator=:" # create Kafka producer

$ kafka-console-consumer --bootstrap-server=localhost:9092 --topic=topic01 --group=G1 # create Kafka consumer (with group)

$ kafka-topics --bootstrap-server=localhost:9092 --list # list topics
$ kafka-topics --bootstrap-server=localhost:9092 --describe --topic=topic01 # get topic details
$ kafka-consumer-groups.sh --bootstrap-server=localhost:9092 --list # list consumers groups
$ kafka-consumer-groups.sh --bootstrap-server=localhost:9092 -—describe --group=G1 # get consumers group details
```

---

#### TODO
- `docs`: documentation of execution, architecture and operation
	* [x] README: execution
	* [x] Figma: architecture
	* [ ] LibreOffice: operation
		1. Architecture
		2. Infrastructure
		3. Classes
		4. Logic
- **Flight Manager**
	> Consumes landing report events and performs logistics (rejects or accepts).  
	> Consumes air traffic information events (flight tracking and weather conditions) and predicts possible logistics issues.  
	> Produces takeoff authorization events.  
	> Produces flight status notification events.  
	- `domain`: action and registry entities
		- `entities`: action and registry entities
			* [x] _App_ [action]
				* [x] _Airport_ [action]
					* [x] _Gate_ [registry]
					* [x] _Flight_ [registry]
		- `enums`: reserved values
			* [x] _LogisticStatusEnum_ [enum]
			* [x] _FlightStatusEnum_ [enum]
			* [x] _PanelStatusEnum_ [enum]
	- `app`: operations, services, and strategies logic
		* [x] _FlightManagerService_ [action]
		* [x] _FlightService_ [action]
		* [x] _GateService_ [action]
	- `infra`:
		- `database`: storage of records
			* _PostgresRepository_
				- [x] Gates
				- [x] Flights
		- `integration`: communication services
			- `rest`: requesting
				* [x] _OpenSkyRestClient_
			- `queue`: messaging
				* [x] _KafkaAdminClient_
					- [x] _TowerReportsConsumer_
					- [x] _FlightNotificationsProducer_
					- [x] _FlightLogisticProducer_
	- `interface`: HTTP endpoints for record querying
		- [x] `[GET] /flights/list` - list of all flights updated within a time interval
		- [x] `[POST] /flights/{flightId}` - register flight
		- [x] `[PUT] /flights/{flightId}` - update flight
- **Control Tower**
	> Generates tower report events with information about new landings (registered or not).  
	> Consumes flight release notification events.  
	> Generates tower report events with takeoff response.  
	> Generates air traffic information events.  
	* [x] _Reporter_
		* Producer
			* [x] _KafkaProducer_
		* Consumer
			* [x] _KafkaConsumer_
- **Client Subscriptions**
	> Notifies flight status change events.  
	* [x] _Subscription_
	* Consumer
		* [x] _KafkaConsumer_
- **Flight-Status Panel**
	> Displays the list of flights and their status within a time interval.  
	* [ ] _PanelSync_
	* RestClient
		* [x] _FlightManagerRestClient_
- **NotificationStreams**
	> Consumes from towerReports topic.  
	* Joins with:
		- logística topic
	* Group by:
		- key
		- time window
	* Send to:
		- monitoring topic
	> Consumes from logistics topic.  
	* Group by:
		- key
	* Filter by:
		- fail events
	* conta/agrega por:
		- fail events
	* Send to:
		- notifications topic


### Topology
```txt

Topologies:
	Sub-topology: 0
	Source: KSTREAM-SOURCE-0000000002 (topics: [towerReports])
		--> KSTREAM-KEY-SELECT-0000000013, KSTREAM-WINDOWED-0000000004, KSTREAM-PEEK-0000000003
	Source: KSTREAM-SOURCE-0000000000 (topics: [flightLogistic])
		--> KSTREAM-WINDOWED-0000000005, KSTREAM-PEEK-0000000001
	Processor: KSTREAM-WINDOWED-0000000004 (stores: [KSTREAM-JOINTHIS-0000000006-store])
		--> KSTREAM-JOINTHIS-0000000006
		<-- KSTREAM-SOURCE-0000000002
	Processor: KSTREAM-WINDOWED-0000000005 (stores: [KSTREAM-JOINOTHER-0000000007-store])
		--> KSTREAM-JOINOTHER-0000000007
		<-- KSTREAM-SOURCE-0000000000
	Processor: KSTREAM-JOINOTHER-0000000007 (stores: [KSTREAM-JOINTHIS-0000000006-store])
		--> KSTREAM-MERGE-0000000008
		<-- KSTREAM-WINDOWED-0000000005
	Processor: KSTREAM-JOINTHIS-0000000006 (stores: [KSTREAM-JOINOTHER-0000000007-store])
		--> KSTREAM-MERGE-0000000008
		<-- KSTREAM-WINDOWED-0000000004
	Processor: KSTREAM-MERGE-0000000008 (stores: [])
		--> KSTREAM-PEEK-0000000009
		<-- KSTREAM-JOINTHIS-0000000006, KSTREAM-JOINOTHER-0000000007
	Processor: KSTREAM-PEEK-0000000009 (stores: [])
		--> KSTREAM-FILTER-0000000010
		<-- KSTREAM-MERGE-0000000008
	Processor: KSTREAM-FILTER-0000000010 (stores: [])
		--> KSTREAM-PEEK-0000000011
		<-- KSTREAM-PEEK-0000000009
	Processor: KSTREAM-KEY-SELECT-0000000013 (stores: [])
		--> KSTREAM-FILTER-0000000017
		<-- KSTREAM-SOURCE-0000000002
	Processor: KSTREAM-FILTER-0000000017 (stores: [])
		--> KSTREAM-SINK-0000000016
		<-- KSTREAM-KEY-SELECT-0000000013
	Processor: KSTREAM-PEEK-0000000011 (stores: [])
		--> KSTREAM-SINK-0000000012
		<-- KSTREAM-FILTER-0000000010
	Processor: KSTREAM-PEEK-0000000001 (stores: [])
		--> none
		<-- KSTREAM-SOURCE-0000000000
	Processor: KSTREAM-PEEK-0000000003 (stores: [])
		--> none
		<-- KSTREAM-SOURCE-0000000002
	Sink: KSTREAM-SINK-0000000012 (topic: flightNotifications)
		<-- KSTREAM-PEEK-0000000011
	Sink: KSTREAM-SINK-0000000016 (topic: KSTREAM-AGGREGATE-STATE-STORE-0000000014-repartition)
		<-- KSTREAM-FILTER-0000000017

Sub-topology: 1
	Source: KSTREAM-SOURCE-0000000018 (topics: [KSTREAM-AGGREGATE-STATE-STORE-0000000014-repartition])
		--> KSTREAM-AGGREGATE-0000000015
	Processor: KSTREAM-AGGREGATE-0000000015 (stores: [KSTREAM-AGGREGATE-STATE-STORE-0000000014])
		--> KTABLE-TOSTREAM-0000000019
		<-- KSTREAM-SOURCE-0000000018
	Processor: KTABLE-TOSTREAM-0000000019 (stores: [])
		--> KSTREAM-MAP-0000000020
		<-- KSTREAM-AGGREGATE-0000000015
	Processor: KSTREAM-MAP-0000000020 (stores: [])
		--> KSTREAM-PEEK-0000000021
		<-- KTABLE-TOSTREAM-0000000019
	Processor: KSTREAM-PEEK-0000000021 (stores: [])
		--> KSTREAM-SINK-0000000022
		<-- KSTREAM-MAP-0000000020
	Sink: KSTREAM-SINK-0000000022 (topic: flightMonitoring)
		<-- KSTREAM-PEEK-0000000021
```

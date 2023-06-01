
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
		* [ ] _GateService_ [action]
	- `infra`:
		- `database`: storage of records
			* _PostgresRepository_
				- [ ] Gates
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
	* [ ] _Reporter_
		* Producer
			* [x] _KafkaProducer_
		* Consumer
			* [x] _KafkaConsumer_
	* RestClients
		* [ ] _FlightManagerRestClient_
- **Flight-Status Panel**
	> Displays the list of flights and their status within a time interval.  
	* [ ] _PanelSync_
		* [ ] _Cron_
	* RestClient
		* [ ] _FlightManagerRestClient_
- **Client Subscriptions**
	> Notifies flight status change events.  
	* [ ] _Subscription_
	* Consumer
		* [ ] _KafkaConsumer_


# Event-Oriented Monitoring System Applied to Flights

by _Álvaro Davi S. Alves_ - **2020101874**  

#### Computer Engineering :computer:
First assignment for the discipline  ```Tópicos Especiais em Informática IV (Event-Oriented Programming)```.  

### Universidade Federal do Espirito Santo ([UFES](https://ufes.br))

<img source="./docs/img/marca_ufes.png" alt="ufes logo" height="150px" width="250px">  

---


## Architecture

[Back-End Architecture](https://www.figma.com/file/DLgbATt7o29ccL3Qz8Gc2r/Arquitetura-Orienta%C3%A7%C3%A3o-a-Eventos)  

## How to run the project

To run this project, you need to have the [JDK version 11](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html) or higher installed and these libraries: [Apache Kafka](https://kafka.apache.org/) and [Apache Maven](https://maven.apache.org/index.html).  
This project has created using:   
```sh
# create Maven project
$ mvn archetype:generate -DgroupId=app -DartifactId=Flight_Manager_System -DarchetypeVersion=1.4 -DinteractiveMode=false
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

- `docs`: documentação de execução, arquitetura e funcionamento
	* [x] README: execução
	* [x] Figma: arquitetura
	* [ ] LaTeX: funcionamento
- **Flight Manager**
	- `domain`: entidades de ação e registro
		- `entities`: entidades de ação e registro
			* [x] _App_ [ação]
			* [ ] _FlightManager_ [ação]
				* [ ] _Airport_ [ação]
					* [ ] _Gate_ [registro]
				* [ ] _Flight_ [ação]
					* [ ] _Airplane_ [registro]
		- `enums`: valores reservados
	- `app`: lógica de operations, services e strategies
	- `infra`:
		- `database`: armazenamento de registros
			* [ ] MySQLClient
				- Flights
				- Gates
		- `cache`: armazenamento temporário de registros e consultas
			* [ ] _RedisClient_
		- `integration`: serviços de comunicação
			- `queue`: messageria
				* [x] KafkaAdminClient
					- [x] KafkaConsumers
					- [x] KafkaProducers
				* HttpClient
					* [ ] _OpenSkyClient_
	- `interface`: endpoints HTTP para consulta aos registros
		- [ ] `[GET] /flights-status`
		- [ ] `[POST] /flight-manager/{flightId}`
- **Control Tower**
	* [ ] _Reporter_
		* Producer
			* [ ] _KafkaProducer_
	* [ ] _FlightTracker_
		* [ ] _Cron_
		* HttpClient
			* [ ] _OpenSkyClient_
- **Flight-Status Panel**
	* [ ] _PanelSync_
		* [ ] _Cron_
	* HttpClient
		* [ ] _FlightManagerClient_
- **Client Subscriptions**
	* [ ] _Subscription_
	* Consumer
		* [ ] _KafkaConsumer_

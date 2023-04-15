
# Event-Oriented Monitoring System Applied to Flights

by _Álvaro Davi S. Alves_ - **2020101874**  

#### Computer Engineering :computer:
First assignment for the discipline  ```Tópicos Especiais em Informática IV (Event-Oriented Programming)```.  

### Universidade Federal do Espirito Santo ([UFES](https://ufes.br))

<img source="./docs/img/marca_ufes.png" alt="ufes logo" height="150px" width="250px">  

---

## How to run the project

To run this project, you need to have the [JDK version 11](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html) or higher installed and these libraries: [Apache Kafka](https://kafka.apache.org/) and [Apache Maven](https://maven.apache.org/index.html).  

After installing the JDK, you can run the project by typing the following commands in the terminal:  
```sh
# create Maven project
$ mvn archetype:generate -DgroupId=app -DartifactId=Flight_Manager_System -DarchetypeVersion=1.4 -DinteractiveMode=false
# install/reinstall dependencies
$ mvn install
# recompile project
$ mvn clean compile
# run project
$ mvn exec:java
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
$ kafka-console-producer --bootstrap-server=localhost:9092 --topic=topic01 # create Kafka producer
$ kafka-console-consumer --bootstrap-server=localhost:9092 --topic=topic01 --group=G1 # create Kafka consumer (with group)

$ kafka-topics --bootstrap-server=localhost:9092 --list # list topics
$ kafka-topics --bootstrap-server=localhost:9092 --describe --topic=topic01 # get topic details
$ kafka-consumer-groups.sh --bootstrap-server=localhost:9092 --list # list consumers groups
$ kafka-consumer-groups.sh --bootstrap-server=localhost:9092 -—describe --group=G1 # get consumers group details
```

---

#### TODO

- `docs`: documentação de execução, arquitetura e funcionamento
	* [ ] README: execução
	* [ ] Figma: arquitetura
	* [ ] LaTeX: funcionamento
- **Flight Manager**
	- `domain`: entidades de ação e registro
		- `entities`: entidades de ação e registro
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
		- `integration`: serviços de comunicação
			- `queue`: messageria
				* [ ] KafkaAdminClient
					- KafkaConsumers
					- KafkaProducers
				* HttpClient
					* [ ] _OpenSkyClient_
			- `cache`: armazenamento temporário de registros e consultas
				* [ ] _RedisClient_
	- `interface`: endpoints HTTP para consulta aos registros
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

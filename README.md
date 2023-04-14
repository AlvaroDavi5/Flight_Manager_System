
# Event-Oriented Monitoring System Applied to Flights

by _Álvaro Davi S. Alves_ - **2020101874**  

#### Computer Engineering :computer:
First assignment for the discipline  ```Tópicos Especiais em Informática IV (Event-Oriented Programming)```.  

### Universidade Federal do Espirito Santo ([UFES](https://ufes.br))

<img source="./docs/img/marca_ufes.png" alt="ufes logo" height="150px" width="250px">  

---

## How to run the project

To run this project, you need to have the [JDK version 11](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html) or higher installed and the libraries: [Apache Kafka](https://kafka.apache.org/), [Apache Ant](https://ant.apache.org/) and [Apache Maven](https://maven.apache.org/index.html).  

After installing the JDK, you can run the project by typing the following commands in the terminal:  
```sh
# create project
$ mvn archetype:generate -DgroupId=app -DartifactId=Flight_Manager_System -DarchetypeVersion=1.4 -DinteractiveMode=false
# run project
$ mvn install
$ mvn clean compile exec:java
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

* [x] Create a Maven `pom.xml` file
* [ ] Create a documentation for the project in PDF format
* [ ] Implement _KafkaAdminClient_, _KafkaProducer_ and _KafkaConsumer_ classes for integration
* [ ] Implement _FlightManager_, _GeneralControl_, _Reporter_ and _FlightTracker_ classes for domain
* [ ] Implement _MySQLClient_ and _RedisClient_ classes for infra
* [ ] Implement **Control Tower Service**
	- _GeneralControl_ class
		- _KafkaConsumer_ class
		- _KafkaProducer_ class
	- _Reporter_ class
		- _KafkaProducer_ class
	- _FlightTracker_ class
		- _HttpClient_ class
* [ ] Implement **Flight Status Manager Service**
	- _FlightManager_ class
		- _Airport_ class
			- _Gate_ class
		- _Flight_ class
			- _Airplane_ class
	- _KafkaConsumer_ class
	- _KafkaProducer_ class
	- _MySQLClient_ class
	- _RedisClient_ class
* [ ] Implement **Flight Status Panel Service**
	- _PanelSync_ class
		- _HttpClient_ class
* [ ] Implement **Client Subscriptions Service**
	- _Subscription_ class
		- _KafkaConsumer_ class

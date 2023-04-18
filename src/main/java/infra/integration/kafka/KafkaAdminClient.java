package infra.integration.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import infra.logging.AppLogger;

public class KafkaAdminClient {
	// attributes
	private Properties producersProperties;
	private Properties consumersProperties;
	private Logger logger;

	// interfaces
	public interface ConsumerHandler {
		void handleMessage(ConsumerRecord<String, String> record);
	}

	// constructor method
	public KafkaAdminClient(Properties producersPros, Properties consumersProps) {
		AppLogger logger = new AppLogger(this.getClass().getName());
		this.logger = logger.getLogger();

		String bootstrapServer = System.getenv("KAFKA_BOOTSTRAP_SERVER");
		bootstrapServer = (bootstrapServer == null) ? "localhost:9092" : bootstrapServer;

		this.producersProperties = producersPros;
		this.producersProperties.setProperty(
				ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
				bootstrapServer);
		this.producersProperties.setProperty(
				ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				StringSerializer.class.getName());
		this.producersProperties.setProperty(
				ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				StringSerializer.class.getName());

		this.consumersProperties = consumersProps;
		this.consumersProperties.setProperty(
				ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
				bootstrapServer);
		this.consumersProperties.setProperty(
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class.getName());
		this.consumersProperties.setProperty(
				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class.getName());
	}

	// methods
	public Properties getProducersProperties() {
		return this.producersProperties;
	}

	public Properties getConsumersProperties() {
		return this.consumersProperties;
	}

	public KafkaProducer<String, String> createProducer(Properties props) {
		return new KafkaProducer<String, String>(props);
	}

	public Boolean sendMessage(KafkaProducer<String, String> producer, String topicName, int partitionSize,
			String key, String value) {
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(topicName, partitionSize, key, value);

		Callback callback = (data, error) -> {
			if (error != null) {
				this.logger.info("Error to send message: " + error.getMessage());
				return;
			}
			this.logger.info(
					"Sended message successfully.\n"
							+ "\tTopic: " + data.topic() + "\n"
							+ "\tOffset: " + data.offset() + "\n"
							+ "\tPartition: " + data.partition() + "\n"
							+ "\tTimestamp: " + data.timestamp() + "\n");
		};

		Boolean sended = producer.send(record, callback).isDone();
		producer.flush();

		return sended;
	}

	public KafkaConsumer<String, String> createConsumer(Properties props) {
		return new KafkaConsumer<String, String>(props);
	}

	// @Overload
	public KafkaConsumer<String, String> createConsumer(Properties props, String groupId) {
		if (!groupId.isEmpty())
			this.consumersProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);

		return new KafkaConsumer<String, String>(props);
	}

	public void listenMessages(KafkaConsumer<String, String> consumer, String topicName, ConsumerHandler handler) {

		consumer.subscribe(Arrays.asList(topicName));
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

			for (ConsumerRecord<String, String> record : records) {
				this.logger.info(
						"Received message.\n"
								+ "\tKey: " + record.key() + "\n"
								+ "\tTimestamp: " + record.timestamp() + "\n");
				handler.handleMessage(record);
			}
		}
	}
}
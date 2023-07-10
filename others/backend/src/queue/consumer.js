const { Kafka } = require('kafkajs');
const dotenv = require('dotenv');
const { webSocketClient } = require('../utils.js');
dotenv.config();


const notificationsTopic = process.env.FLIGHT_NOTIFICATIONS_TOPIC;
const MonitoringTopic = process.env.FLIGHT_MONITORING_TOPIC;

function _formatMessageBeforeSend(message) {
	let msg = '';

	try {
		msg = JSON.stringify(message);
	}
	catch (error) {
		msg = String(message);
	}

	return msg;
};
function _formatMessageAfterReceive(message) {
	let msg = '';

	try {
		msg = JSON.parse(message);
	}
	catch (error) {
		msg = String(message);
	}

	return msg;
};

const kafka = new Kafka({
	clientId: 'websocket-consumer',
	brokers: ['localhost:9092']
});
const consumer = kafka.consumer({ groupId: 'group1' });

const handleMessage = async ({ topic, partition, message }) => {
	const { key, value } = message;
	console.log(`Received message from topic: ${topic} partition: ${partition} with key: ${key}`);
	const finalKey = String(key);
	let finalValue = _formatMessageAfterReceive(String(value));

	if (topic === MonitoringTopic) {
		const keyValueAndInterval = finalKey.split('/');
		const keyValue = keyValueAndInterval[0].split('@');
		const Interval = keyValueAndInterval[1].split('|');
		finalValue = {
			key: keyValue[0],
			value: keyValue[1],
			startAt: Interval[0],
			endAt: Interval[1],
		};
	}

	webSocketClient.send(
		'sendMessage',
		_formatMessageBeforeSend({
			topic: String(topic), message: { key: finalKey, value: finalValue }
		}),
	);
};

const runConsumer = async () => {
	await consumer.connect();

	await consumer.subscribe({ topic: notificationsTopic });
	await consumer.subscribe({ topic: MonitoringTopic });

	await consumer.run({
		eachMessage: handleMessage
	});
};

module.exports = {
	consumer,
	runConsumer,
};

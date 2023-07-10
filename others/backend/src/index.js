const dotenv = require('dotenv');
dotenv.config();
const { runConsumer } = require('./queue/consumer.js');
const { httpServer, webSocketServer } = require('./utils.js');


// HTTP WebSocket init
webSocketServer.init();

// HTTP REST Server start
const appPort = parseInt(process.env.APP_PORT) || 4001;
httpServer.listen(appPort, () => {
	console.log(`\nAplication started on port ${appPort}\n`);
});

// Queue Consumer start
runConsumer().catch(console.error);
console.log('Started Queue Consumer');

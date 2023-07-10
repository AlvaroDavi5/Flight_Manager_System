const { webSocketClient } = require('../src/utils.js');


webSocketClient.send(
	'testEvent',
	JSON.stringify({
		from: 'me',
		to: 'you',
	})
);

const { io } = require('socket.io-client');


class WebSocketClient {

	constructor(socketUrl) {
		this.socket = io(socketUrl);
	};

	// send message to the server
	send(event, msg) {

		this.socket.emit(
			String(event),
			msg,
		);

	};

	// receive message from the server
	receive(event, callback) {

		this.socket.on(
			String(event),
			callback,
		);

	};

};


module.exports = WebSocketClient;

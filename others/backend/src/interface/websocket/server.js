
class WebSocketServer {

	constructor(webSocketServer) {
		this.server = webSocketServer;
		this.clients = [];
	};

	_formatMessageBeforeSend(message) {
		let msg = '';

		try {
			msg = JSON.stringify(message);
		}
		catch (error) {
			msg = String(message);
		}

		return msg;
	};
	_formatMessageAfterReceive(message) {
		let msg = '';

		try {
			msg = JSON.parse(message);
		}
		catch (error) {
			msg = String(message);
		}

		return msg;
	};

	init() {
		const messages = [];
		const formatBeforeSend = (message) => this._formatMessageBeforeSend(message);
		const formatAfterReceive = (message) => this._formatMessageAfterReceive(message);

		// connection started
		this.server.on(
			'connection',
			(socket) => {
				this.clients.push(socket);
				console.log(`Client connected: ${socket.id}`);

				// connection closed
				socket.on(
					'disconnect',
					(msg) => {
						console.log(`Client disconnected: ${socket.id}`);
						this.clients.splice(this.clients.indexOf(socket), 1);
					},
				);

				// listen send event from the client and send message to the client
				socket.on(
					'sendMessage',
					function (msg) {
						msg = formatAfterReceive(msg);
						console.log('Sended Message:', msg);
						messages.push(msg);

						socket.broadcast.emit(
							'receivedMessage',
							formatBeforeSend(msg),
						); // send to all clients except the sender
					},
				);

				socket.emit('previousMessages', JSON.stringify({ messages }));
			},
		);

		console.log('Initialized WebSocket');
	};

};


module.exports = WebSocketServer;

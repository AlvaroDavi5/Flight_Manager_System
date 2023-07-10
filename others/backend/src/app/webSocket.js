const { Server } = require('socket.io');


function initWebSocket(httpServer) {
	// WebSocket assignment
	const webSocket = new Server(httpServer, {
		cors: {
			origin: '*',
			allowedHeaders: '*',
			methods: ['GET', 'POST', 'PUT'],
		}
	});

	return webSocket;
}

module.exports = initWebSocket;

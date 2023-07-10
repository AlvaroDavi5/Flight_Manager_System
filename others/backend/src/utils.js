const dotenv = require('dotenv');
dotenv.config();
const createRestApi = require('./app/restApi.js');
const createHttpServer = require('./app/httpServer.js');
const initWebSocket = require('./app/webSocket.js');
const WebSocketServer = require('./interface/websocket/server.js');
const WebSocketClient = require('./interface/websocket/client.js');


// server assignment
const restApi = createRestApi();
const httpServer = createHttpServer(restApi);

// websocket connection
const webSocket = initWebSocket(httpServer);
const webSocketUrl = process.env.APP_URL || 'http://localhost:3000/';
const webSocketServer = new WebSocketServer(webSocket);
const webSocketClient = new WebSocketClient(webSocketUrl);


module.exports = {
	httpServer,
	webSocket,
	webSocketServer,
	webSocketClient,
};

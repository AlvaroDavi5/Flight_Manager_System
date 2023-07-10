const http = require('http');


function createServer(app) {
	// HTTP server assignment
	const server = http.createServer(app);

	console.log('Created HTTP Server');
	return server;
}

module.exports = createServer;

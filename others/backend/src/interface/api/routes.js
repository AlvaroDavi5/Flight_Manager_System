
module.exports = function (app) {

	// --- index ---
	app.get("/", (request, response) => {
		console.log(`[${request?.method}] ${request.url} - ${response?.statusCode}`);

		response.status(200);
		response.send({
			method: request?.method,
			url: request?.baseUrl,
			statusCode: response?.statusCode,
			status: 'OK',
		});
	});

	app.get('/home', (request, response) => {
		console.log(`[${request?.method}] ${request.url} - ${response?.statusCode}`);

		response.status(200);
		response.render('index.html');
	});
}

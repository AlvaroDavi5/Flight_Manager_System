const path = require('path');
const ejs = require('ejs');
const bodyParser = require('body-parser');
const express = require('express');
const router = require('../interface/api/routes.js');


function createRestApi() {
	// REST API assignment
	const app = express();

	// REST API configuration
	app.use(bodyParser.json());
	app.use(bodyParser.urlencoded({ extended: false }));
	app.set('views', path.join(__dirname, '..', 'frontend'));
	app.engine('html', ejs.renderFile);
	app.set('view engine', 'html');

	// endpoints definition
	router(app);
	console.log('Created REST API');

	return app;
};

module.exports = createRestApi;

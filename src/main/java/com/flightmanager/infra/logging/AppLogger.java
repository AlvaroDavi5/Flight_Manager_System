package com.flightmanager.infra.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;

public class AppLogger {
	private Logger logger;

	public AppLogger(String name) {
		this.logger = LogManager.getLogger(name);
	}

	public Logger getLogger() {
		return this.logger;
	}

	public void fatal(Marker marker, String message) {
		this.logger.fatal(marker, message);
	}

	public void error(Marker marker, String message) {
		this.logger.error(marker, message);
	}

	public void warn(Marker marker, String message) {
		this.logger.warn(marker, message);
	}

	public void info(Marker marker, String message) {
		this.logger.info(marker, message);
	}

	public void debug(Marker marker, String message) {
		this.logger.debug(marker, message);
	}

	public void trace(Marker marker, String message) {
		this.logger.trace(marker, message);
	}
}

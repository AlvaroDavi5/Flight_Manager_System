package infra.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {
	private Logger logger;

	public AppLogger(String name) {
		this.logger = LoggerFactory.getLogger(name);
	}

	public Logger getLogger() {
		return this.logger;
	}
}

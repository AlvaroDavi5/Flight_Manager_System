package com.flightmanager.app.utils;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.stream.Collectors;

public class FileUtils {
	public FileUtils() {
	}

	public Properties readPropertiesFile(String path) throws IOException {
		Properties props = new Properties();

		try {
			FileReader reader = new FileReader(path);
			BufferedReader buffer = new BufferedReader(reader);

			props.load(buffer);
			buffer.close();
		} catch (Exception exception) {
			System.out.println("FileUtils.Exception → " + exception.getMessage());
		}

		return props;
	}

	public String getInput() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		return reader.lines().collect(Collectors.joining("\n"));
	}
}

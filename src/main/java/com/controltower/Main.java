package com.controltower;

import com.controltower.app.App;

public class Main {
	public static void main(String[] args) {
		try {
			if (args.length < 3)
				throw new Error("Invalid number of arguments (expected 3 arguments)", null);

			App kafkaApp = new App(args);

			kafkaApp.start();
		} catch (Error error) {
			System.out.println("Main.Error → " + error.getMessage());
			error.printStackTrace();
			System.exit(0);
		} catch (Exception exception) {
			System.out.println("Main.Exception → " + exception.getMessage());
			exception.printStackTrace();
			System.exit(1);
		}
	}
}

package com.controltower;

import com.controltower.app.App;

public class Main {
	public static void main(String[] args) {
		try {
			if (args.length < 1)
				throw new Error("Invalid number of arguments (expected 1 argument)", null);

			App app = new App(args);

			System.out.println(" --- App Started --- ");
			app.start();
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

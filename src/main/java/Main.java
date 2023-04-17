import java.lang.Error;
import java.lang.Exception;
import java.io.IOException;
import java.util.Properties;
import app.utils.FileUtils;
import app.App;

public class Main {
	public static void main(String[] args) throws IOException {
		System.out.println("----- STARTED -----\n\n");

		try {
			if (args.length < 2)
				throw new Error("Invalid number of arguments (expected 2 arguments)", null);

			FileUtils utils = new FileUtils();

			Properties producersProperties = utils.readPropertiesFile(args[0]);
			Properties consumerProperties = utils.readPropertiesFile(args[1]);

			App app = new App(producersProperties, consumerProperties);
			app.start();
		} catch (Error error) {
			System.out.println("Main.Error: " + error.getMessage());
			System.exit(0);
		} catch (Exception exception) {
			System.out.println("Main.Exception:");
			exception.printStackTrace();
			System.exit(1);
		}

		System.out.println("\n\n----- FINISHED -----");
	}
}

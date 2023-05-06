import java.lang.Error;
import java.lang.Exception;
import java.io.IOException;
import java.util.Properties;
import app.utils.FileUtils;
import app.App;

public class Main {
	public static void main(String[] args) throws IOException {
		try {
			if (args.length < 2)
				throw new Error("Invalid number of arguments (expected 2 arguments)", null);

			FileUtils utils = new FileUtils();

			Properties producersProperties = utils.readPropertiesFile(args[0]);
			Properties consumersProperties = utils.readPropertiesFile(args[1]);

			App app = new App(producersProperties, consumersProperties);
			app.start();
		} catch (Error error) {
			System.out.print("Main.Error → ");
			error.printStackTrace();
			System.exit(0);
		} catch (Exception exception) {
			System.out.print("Main.Exception → ");
			exception.printStackTrace();
			System.exit(1);
		}
	}
}

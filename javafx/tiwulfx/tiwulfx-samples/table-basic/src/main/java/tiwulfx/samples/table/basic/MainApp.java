package tiwulfx.samples.table.basic;

import com.panemu.tiwulfx.common.TiwulFXUtil;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tiwulfx.samples.shared.DataGenerator;

public class MainApp extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		DataGenerator.createWithTestData(1000);
		TiwulFXUtil.addLiteralBundle("tiwulfx.samples.shared.translation");
		final FrmPerson frmPerson = new FrmPerson();
		frmPerson.reload();
		Scene scene = new Scene(frmPerson);
		TiwulFXUtil.setTiwulFXStyleSheet(scene);
		stage.setTitle("Basic TableControl");
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * The main() method is ignored in correctly deployed JavaFX application.
	 * main() serves only as fallback in case the application can not be launched
	 * through deployment artifacts, e.g., in IDEs with limited FX support.
	 * NetBeans ignores main().
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
package screen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class Menu extends Application {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
	public void start(Stage stage) throws Exception {
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));

			setScene(stage, root, WIDTH, HEIGHT);
			stage.setOnCloseRequest(event -> {
				event.consume();
				quit(stage);
			});
			stage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

    public static void main(String[] args) {
        launch(args);
    }
}

package n25;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        StackPane root = new StackPane();
        Pane pane = new Pane();
        // gọi phương thức vẽ hình tại đây. ví dụ antigen.draw(pane);
        Envelope envelope = new Envelope(new Location(200, 200), 150, 5, Color.YELLOW, Color.RED);
        envelope.draw(pane, SubComponentType.GLYCOPROTEIN);
        MatrixProtein matrixProtein = new MatrixProtein(new Location(200, 200), 135, 5, Color.BROWN, Color.BLACK);
        matrixProtein.draw(pane, SubComponentType.NONE);
        Capsit capsit = new Capsit(new Location(200, 200), 100, 5, Color.GOLD, Color.BLUE);
        capsit.draw(pane, ComponentStyle.HEXAGON_STYLE, SubComponentType.ANTIGEN);
        Nucleoid nucleoid = new Nucleoid(new Location(200, 200), 50, 5, Color.GREEN, Color.RED);
        nucleoid.draw(pane, SubComponentType.NONE, 10);
        //
        root.getChildren().add(pane);
        scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
    }   

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
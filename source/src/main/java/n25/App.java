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
        Capsit capsit = new Capsit(new Location(200, 200), 100, 5, pane, Color.RED);
        capsit.changeStage(capsit.HEXAGON_STAGE);
        capsit.draw(pane);
        Capsit capsit2 = new Capsit(new Location(200, 200), 100, 5, pane, Color.BLUE);
        capsit2.changeStage(capsit2.CIRCLE_STAGE);
        capsit2.draw(pane);
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
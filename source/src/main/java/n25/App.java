package n25;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

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
        SarCoV2 sarCoV2 = new SarCoV2("Sar CoV 2", new Location(200,300), 50, 3);
        sarCoV2.displayInfection(pane, 100);
        //
        root.getChildren().add(pane);
        scene = new Scene(root, 1000, 900);
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
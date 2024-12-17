package n25;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import n25.virus.HAV;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Pane createSelectionPane()
    {
        Pane pane = new Pane();
        // Tạo label và combobox để chọn loại virus
        Label label = new Label("Select type of virus:");
        label.setLayoutX(10);
        label.setLayoutY(10);
        pane.getChildren().add(label);

        ComboBox<String> cbSelectType = new ComboBox<>();
        cbSelectType.getItems().addAll("Enveloped virus", "Non-enveloped virus");
        cbSelectType.setLayoutX(10);
        cbSelectType.setLayoutY(30);
        pane.getChildren().add(cbSelectType);

        // Tạo label và combobox để chọn virus
        Label label2 = new Label("Select virus:");
        label2.setLayoutX(10);
        label2.setLayoutY(60);
        pane.getChildren().add(label2);

        ComboBox<String> cbSelectVirus = new ComboBox<>();
        cbSelectVirus.getItems().addAll("Select type of virus first");
        cbSelectVirus.setLayoutX(10);
        cbSelectVirus.setLayoutY(80);
        pane.getChildren().add(cbSelectVirus);

        // Tạo label và combobox để chọn loại hiển thị
        Label label3 = new Label("Select display type:");
        label3.setLayoutX(10);
        label3.setLayoutY(110);
        pane.getChildren().add(label3);

        ComboBox<String> cbSelectDisplay = new ComboBox<>();
        cbSelectDisplay.getItems().addAll("Structure", "Infection Mechanism");
        cbSelectDisplay.setValue("Structure");
        cbSelectDisplay.setLayoutX(10);
        cbSelectDisplay.setLayoutY(130);
        pane.getChildren().add(cbSelectDisplay);

        return pane;
    }
    @Override
    public void start(Stage stage) throws IOException {
        Pane root = new Pane();
        root.getChildren().add(createSelectionPane());
        HAV hav = new HAV("HAV", new Location(400, 300), 50, 3);
        hav.displayInfection(root, 100);
        scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
package n25;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import n25.virus.HAV;
import n25.virus.HIV;
import n25.virus.Virus;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Virus displayedVirus;
    private static Pane createSelectionPane()
    {
        // Khai báo các combobox
        ComboBox<String> cbSelectType = new ComboBox<>();
        ComboBox<String> cbSelectVirus = new ComboBox<>();
        ComboBox<String> cbSelectDisplay = new ComboBox<>();

        Pane pane = new Pane();
        // Tạo label và combobox để chọn loại virus
        Label label = new Label("Select type of virus:");
        label.setLayoutX(10);
        label.setLayoutY(10);
        pane.getChildren().add(label);

        cbSelectType.getItems().addAll("Enveloped virus", "Non-enveloped virus");
        cbSelectType.setLayoutX(10);
        cbSelectType.setLayoutY(30);
        cbSelectType.setPrefSize(150, 20);
        cbSelectType.setOnAction( 
            e -> {
                if (cbSelectType.getValue().equals("Enveloped virus"))
                {
                    cbSelectVirus.getItems().clear();
                    cbSelectVirus.getItems().addAll("HIV", "SARS-CoV-2");
                    cbSelectVirus.setValue("HIV");
                    
                }
                else if (cbSelectType.getValue().equals("Non-enveloped virus"))
                {
                    cbSelectVirus.getItems().clear();
                    cbSelectVirus.getItems().addAll("HAV", "Rhinovirus");
                    cbSelectVirus.setValue("HAV");
                }
            }
        );
        pane.getChildren().add(cbSelectType);

        // Tạo label và combobox để chọn virus
        Label label2 = new Label("Select virus:");
        label2.setLayoutX(10);
        label2.setLayoutY(60);
        pane.getChildren().add(label2);

        cbSelectVirus.getItems().addAll("Select type of virus first");
        cbSelectVirus.setLayoutX(10);
        cbSelectVirus.setLayoutY(80);
        cbSelectVirus.setPrefSize(150, 20);
        pane.getChildren().add(cbSelectVirus);

        // Tạo label và combobox để chọn loại hiển thị
        Label label3 = new Label("Select display type:");
        label3.setLayoutX(10);
        label3.setLayoutY(110);
        pane.getChildren().add(label3);

        cbSelectDisplay.getItems().addAll("Structure", "Infection Mechanism");
        cbSelectDisplay.setValue("Structure");
        cbSelectDisplay.setLayoutX(10);
        cbSelectDisplay.setLayoutY(130);
        cbSelectDisplay.setPrefSize(150, 20);
        pane.getChildren().add(cbSelectDisplay);

        // Tạo button để bắt đầu hiển thị
        Button btnDisplay = new Button("Display");
        btnDisplay.setLayoutX(10);
        btnDisplay.setLayoutY(160);
        btnDisplay.setPrefSize(150, 20);
        btnDisplay.setOnMouseClicked(e -> {
            if (displayedVirus != null)
            {
                displayedVirus.dispose();
            }
            switch (cbSelectVirus.getValue()) {
                case "HIV":
                    HIV hiv = new HIV("HIV", new Location(400, 300), 50, 3);
                    displayedVirus = hiv;
                    break;
                case "HAV":
                    HAV hav = new HAV("HAV", new Location(400, 300), 50, 3);
                    displayedVirus = hav;
                    break;
                default:
                    break;
            }
            if (cbSelectDisplay.getValue().equals("Structure"))
            {
                displayedVirus.displayStructure(pane);
            }
            else if (cbSelectDisplay.getValue().equals("Infection Mechanism"))
            {
                displayedVirus.displayInfection(pane, 100);
            }
        });
        pane.getChildren().add(btnDisplay);
        
        // Tạo button để tạm dừng
        Button btnPause = new Button("Pause");
        btnPause.setLayoutX(10);
        btnPause.setLayoutY(190);
        btnPause.setPrefSize(150, 20);
        pane.getChildren().add(btnPause);

        // Tạo button để hủy trình diễn
        Button btnCancel = new Button("Pause");
        btnCancel.setLayoutX(10);
        btnCancel.setLayoutY(220);
        btnCancel.setPrefSize(150, 20);
        btnCancel.setPrefSize(150, 20);
        pane.getChildren().add(btnCancel);

        // Tạo button hiển thị help
        Button btnHelp = new Button("Help");
        btnHelp.setLayoutX(10);
        btnHelp.setLayoutY(250);
        btnHelp.setPrefSize(150, 20);
        pane.getChildren().add(btnHelp);

        // Tạo button để thoát
        Button btnExit = new Button("Exit");
        btnExit.setLayoutX(10);
        btnExit.setLayoutY(280);
        btnExit.setPrefSize(150, 20);
        pane.getChildren().add(btnExit);
        return pane;
    }
    @Override
    public void start(Stage stage) throws IOException {
        Pane root = new Pane();
        root.getChildren().add(createSelectionPane());
        scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
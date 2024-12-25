package n25;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import n25.virus.HAV;
import n25.virus.HIV;
import n25.virus.Rhinovirus;
import n25.virus.SarCoV2;
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
        
        // Khai báo label4
        Label label4 = new Label("");

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
                    HIV hiv = new HIV("", new Location(250, 300), 50, 3);
                    displayedVirus = hiv;
                    break;
                case "HAV":
                    HAV hav = new HAV(new Location(250, 300), 50, 3);
                    displayedVirus = hav;
                    break;
                case "SARS-CoV-2":
                    SarCoV2 sarCoV2 = new SarCoV2(new Location(250, 300), 50, 3);
                    displayedVirus = sarCoV2;
                    break;
                case "Rhinovirus":
                    Rhinovirus rhinovirus = new Rhinovirus(new Location(250, 300), 50, 3);
                    displayedVirus = rhinovirus;
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
                Timeline statusUpdater = new Timeline();
                statusUpdater.setCycleCount(Timeline.INDEFINITE);
                statusUpdater.getKeyFrames().add(new KeyFrame(javafx.util.Duration.millis(100), e2 -> {
                    if (displayedVirus.status.equals("Completed"))
                    {
                        displayedVirus.dispose();
                        displayedVirus.displayStructure(pane);
                        statusUpdater.stop();
                    }
                    label4.setText(displayedVirus.status);
                }));
                statusUpdater.play();
                displayedVirus.displayInfection(pane, 100);
            }
        });
        pane.getChildren().add(btnDisplay);
        
        // Tạo button để tạm dừng
        Button btnPause = new Button("Pause");
        btnPause.setLayoutX(10);
        btnPause.setLayoutY(190);
        btnPause.setPrefSize(150, 20);
        btnPause.setOnMouseClicked(e -> {
            if (btnPause.getText().equals("Pause"))
            {
                displayedVirus.pause(); 
                btnPause.setText("Resume");
            }
            else
            {
                displayedVirus.resume();
                btnPause.setText("Pause");
            }
        });
        pane.getChildren().add(btnPause);

        // Tạo button hiển thị help
        Button btnHelp = new Button("Help");
        btnHelp.setLayoutX(10);
        btnHelp.setLayoutY(220);
        btnHelp.setPrefSize(150, 20);
        btnHelp.setOnMouseClicked(e -> {
            Stage helpStage = new Stage();
            Pane helpPane = new Pane();
            Label helpLabel = new Label("USER MANUAL\n1. Main Screen\n- Select type of virus: Choose the type of virus to explore: Enveloped Virus or non-enveloped Virus.\n- Select virus: Choose the virus to explore.\n- Select display type: Choose the display type: Structure or Infection Mechanism.\n- Display: Start displaying the selected virus.\n- Pause: Pause the display of the virus.\n- Resume: Resume the display of the virus.\n- Help: Show User Manual screen and program information.\n- Quit: Exit the program.\n\n2. List of Virus\nThe program has 4 available viruses for exploration. Each virus belongs to one of 2 groups: Enveloped Virus and non-enveloped Virus. At main screen, when user clicks on Virus With Envelope or Virus Without Envelope button, the main screen will display as below.\n\nEach cell displays the information and 2 operations: Examine and Inspect Infection.\n-	Examine: Allow users to examine the structure of each virus through its diagram and the diagram description.\n-	Inspect Infection: Allow users to watch the infection process of each virus.\n\nABOUT\n- Program: VirusExplorer \n- Version: 1.0 \n- Purpose: Allow users to have a visually attractive and educational tool to study viruses in terms of structure and their infection process.\n- Programming Language, Libraries, Frameworks: Java 19 + JavaFX 21 \n- Language: English \n- Developed by: OOP Group 25");
            helpLabel.setLayoutX(10);
            helpLabel.setLayoutY(10);
            helpLabel.setWrapText(true);
            helpLabel.setPrefSize(780, 480);
            helpPane.getChildren().add(helpLabel);
            helpPane.setPrefSize(800, 500);
            Scene helpScene = new Scene(helpPane, 800, 500);
            helpStage.setScene(helpScene);
            helpStage.show();
        });
        pane.getChildren().add(btnHelp);

        // Tạo button để thoát
        Button btnExit = new Button("Exit");
        btnExit.setLayoutX(10);
        btnExit.setLayoutY(250);
        btnExit.setPrefSize(150, 20);
        btnExit.setOnMouseClicked(e -> {
            System.exit(0);
        });
        pane.getChildren().add(btnExit);

        // Tạo label để hiển thị thông báo
        label4.setLayoutX(10);
        label4.setLayoutY(280);
        pane.getChildren().add(label4);
        return pane;
    }
    @Override
    public void start(Stage stage) throws IOException {
        Pane root = new Pane();
        root.getChildren().add(createSelectionPane());
        scene = new Scene(root, 1000, 650);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        
    }
}
package n25.screen;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainScreenController {

    // Thuộc tính biểu thị một Virus
    private static class Virus {
        private String name;
        private String description;
        private String imagePath;
        private String infoPath;

        public Virus(String name, String description, String imagePath, String infoPath) {
            this.name = name;
            this.description = description;
            this.imagePath = imagePath;
            this.infoPath = infoPath;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getImagePath() {
            return imagePath;
        }

        public String getInfoPath() {
            return infoPath;
        }
    }

    private final List<Virus> virusWithEnvelope = new ArrayList<>();
    private final List<Virus> virusWithoutEnvelope = new ArrayList<>();
    private List<Virus> virusListToExplore = new ArrayList<>();

    @FXML
    private Button btnHelp, btnQuit, btnEnvelope, btnNoEnvelope, btnReturn;
    @FXML
    private ScrollPane scrollPane_in_stack;
    @FXML
    private VBox vbox_in_stack;
    @FXML
    private GridPane gridPane;

    public MainScreenController() {
        // Khởi tạo danh sách virus
        initializeVirusData();
    }

    // Phương thức khởi tạo dữ liệu virus
    private void initializeVirusData() {
        // Virus thuộc loại "With Envelope"
        virusWithEnvelope.add(new Virus("HIV", "Human Immunodeficiency Virus", "path/to/hiv.png", "path/to/hiv.txt"));
        virusWithEnvelope.add(new Virus("Covid19", "Coronavirus Disease 2019", "path/to/covid19.png", "path/to/covid19.txt"));

        // Virus thuộc loại "Without Envelope"
        virusWithoutEnvelope.add(new Virus("HAV", "Hepatitis A Virus", "path/to/hav.png", "path/to/hav.txt"));
        virusWithoutEnvelope.add(new Virus("Rhinovirus", "Common Cold Virus", "path/to/rhinovirus.png", "path/to/rhinovirus.txt"));
    }

    // Hiển thị danh sách virus "With Envelope"
    @FXML
    public void btnEnvelopeClicked(ActionEvent e) {
        virusListToExplore = virusWithEnvelope;
        updateUI();
    }

    // Hiển thị danh sách virus "Without Envelope"
    @FXML
    public void btnNoEnvelopeClicked(ActionEvent e) {
        virusListToExplore = virusWithoutEnvelope;
        updateUI();
    }

    // Quay lại màn hình chính
    @FXML
    public void btnReturnClicked(ActionEvent e) {
        vbox_in_stack.setVisible(true);
        scrollPane_in_stack.setVisible(false);
        virusListToExplore.clear();
    }

    // Hiển thị màn hình Help
    @FXML
    public void btnHelpClicked(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Help.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(new Scene(root));
            stage.setTitle("Help");
            stage.show();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

  
   @FXML
public void btnQuitClicked(ActionEvent e) {
   
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirm Exit");
    alert.setHeaderText("Do you really want to quit?");
    alert.setContentText("Click OK to confirm or Cancel to return.");

    // Hiển thị hộp thoại và xử lý phản hồi
    ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

    if (result == ButtonType.OK) {
        // Thoát chương trình nếu người dùng xác nhận
        Platform.exit();
        System.exit(0);
    }
}


    // Hiển thị danh sách virus trong GridPane
    private void updateUI() {
        vbox_in_stack.setVisible(false);
        scrollPane_in_stack.setVisible(true);
        gridPane.getChildren().clear();

        showVirusListToExplore();
    }

    // Hiển thị danh sách virus trong GridPane
    private void showVirusListToExplore() {
        final String ITEM_FXML = "VirusItem.fxml";
        int column = 0;
        int row = 1;
        int maxColumns = 3;

        for (Virus virus : virusListToExplore) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(ITEM_FXML));
                VirusItemController controller = new VirusItemController(virus.getName(), virus.getDescription(), virus.getImagePath(), virus.getInfoPath());
                loader.setController(controller);
                AnchorPane pane = loader.load();

                controller.setData();

                if (column == maxColumns) {
                    column = 0;
                    row++;
                }
                gridPane.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(10));
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}

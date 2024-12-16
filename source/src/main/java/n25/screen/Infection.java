package n25.screen;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Infection {

    @FXML
    private TextArea taVirusDescription;
    @FXML
    private TextArea taInfectionProcess;
    @FXML
    private ImageView ivVirusImage;

    private String virusName;
    private String virusDescription;
    private String infectionProcess;
    private String virusImagePath;

    public Infection(String virusName, String virusDescription, String infectionProcess, String virusImagePath) {
        this.virusName = virusName;
        this.virusDescription = virusDescription;
        this.infectionProcess = infectionProcess;
        this.virusImagePath = virusImagePath;
    }

    // Phương thức thiết lập dữ liệu cho giao diện
    public void setData() {
        // Sử dụng virusName để hiển thị trong giao diện hoặc log
        System.out.println("Showing details for virus: " + virusName);

        taVirusDescription.setText("Virus: " + virusName + "\n\n" + virusDescription);
        taInfectionProcess.setText(infectionProcess);

        if (virusImagePath != null && !virusImagePath.isEmpty()) {
            Image image = new Image("file:" + virusImagePath);
            ivVirusImage.setImage(image);
        }
    }
    public static void showInfection(String virusName, String virusDescription, String infectionProcess, String virusImagePath) {
        try {
            FXMLLoader loader = new FXMLLoader(Infection.class.getResource("Infection.fxml"));
            Infection controller = new Infection(virusName, virusDescription, infectionProcess, virusImagePath);
            loader.setController(controller);

            Parent root = loader.load();
            controller.setData();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setTitle("Infection Process - " + virusName); // Sử dụng virusName làm tiêu đề
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

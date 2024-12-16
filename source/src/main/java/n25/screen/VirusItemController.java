package n25.screen;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class VirusItemController {

    @FXML
    private TextField tfVirusName;

    private String virusName;
    private String virusDescription;
    private String virusImagePath;
    private String infectionProcess;

    // Constructor với đầy đủ tham số
    public VirusItemController(String virusName, String virusDescription, String virusImagePath, String infectionProcess) {
        this.virusName = virusName;
        this.virusDescription = virusDescription;
        this.virusImagePath = virusImagePath;
        this.infectionProcess = infectionProcess;
    }
    public void setData() {
        tfVirusName.setText(virusName);
    }
    @FXML
    public void btnInfectionProcessClicked() {
        Infection.showInfection(virusName, virusDescription, infectionProcess, virusImagePath);
    }
}

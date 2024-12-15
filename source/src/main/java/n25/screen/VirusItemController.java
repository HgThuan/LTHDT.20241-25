package n25.screen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import virus.Virus;

import java.io.IOException;

public class VirusItemController {
    @FXML
    private TextField tfVirusName;
    private Virus virus;

    public VirusItemController(Virus virus) {
        this.virus = virus;
    }

    public void setData() {
        tfVirusName.setText(virus.getName());
    }

    public void btnExamineVirusClicked(ActionEvent e) {
        try {
            final String ILLUSTRATION_FXML = "Illustration.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ILLUSTRATION_FXML));
            IllustrationController illustrationController = new IllustrationController(virus);

            fxmlLoader.setController(illustrationController);
            Parent root = fxmlLoader.load();

            illustrationController.setIllustration();

            Stage illustrationStage = new Stage();
            illustrationStage.initModality(Modality.NONE);
            illustrationStage.initOwner(((Node) e.getSource()).getScene().getWindow());
            illustrationStage.setScene(new Scene(root));
            illustrationStage.setTitle("Illustration");
            illustrationStage.show();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void btnInspectInfectionClicked(ActionEvent e) {
        try {
            final String INSPECT_INFECTION_FXML = "InspectInfection.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(INSPECT_INFECTION_FXML));

            InspectInfectionController inspectInfectionController = new InspectInfectionController(virus);
            fxmlLoader.setController(inspectInfectionController);

            AnchorPane root = fxmlLoader.load();

            Stage inspectInfectionStage = new Stage();
            inspectInfectionStage.initModality(Modality.NONE);
            inspectInfectionStage.initOwner(((Node) e.getSource()).getScene().getWindow());
            inspectInfectionStage.setScene(new Scene(root));
            inspectInfectionStage.setTitle("Infection process");
            inspectInfectionStage.show();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
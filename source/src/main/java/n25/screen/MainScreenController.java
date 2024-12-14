package screen;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import virus.Virus;
import virus.VirusList;
import virus.VirusWithEnvelope;
import virus.VirusWithoutEnvelope;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class MainScreenController {
    private final VirusList virusList;
    private ArrayList<Virus> virusListToExplore = new ArrayList<>();
    @FXML
    private Button btnHelp;
    @FXML
    private Button btnQuit;
    @FXML
    private ScrollPane scrollPane_in_stack;
    @FXML
    private VBox vbox_in_stack;
    @FXML
    private Button btnEnvelope;
    @FXML
    private Button btnNoEnvelope;
    @FXML
    private GridPane gridPane;

    public MainScreenController(VirusList virusList) {
        this.virusList = virusList;
    }

    @FXML
    public void btnEnvelopeClicked(ActionEvent e) {
        for (Virus v : virusList.getListOfVirus()) {
            if (v instanceof VirusWithEnvelope) virusListToExplore.add(v);
        }

        vbox_in_stack.setVisible(false);
        scrollPane_in_stack.setVisible(true);
        gridPane.getChildren().clear();

        showVirusListToExplore();
    }

    @FXML
    public void btnNoEnvelopeClicked(ActionEvent e) {
        for (Virus v : virusList.getListOfVirus()) {
            if (v instanceof VirusWithoutEnvelope) virusListToExplore.add(v);
        }

        vbox_in_stack.setVisible(false);
        scrollPane_in_stack.setVisible(true);
        gridPane.getChildren().clear();

        showVirusListToExplore();
    }

    public void showVirusListToExplore() {
        final String ITEM_FXML = "VirusItem.fxml";
        int column = 0;
        int row = 1;

        for (Virus virus : virusListToExplore) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource(ITEM_FXML));

                VirusItemController virusItemController = new VirusItemController(virus);
                fxmlLoader.setController(virusItemController);

                AnchorPane anchorPane = fxmlLoader.load();

                virusItemController.setData();

                if (column == 3) {
                    column = 0;
                    row += 1;
                }

                gridPane.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(20, 10, 10, 10));
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    @FXML
    public void btnReturnClicked(ActionEvent e) {
        if (!vbox_in_stack.isVisible()) {
            vbox_in_stack.setVisible(true);
            scrollPane_in_stack.setVisible(false);
            virusListToExplore = new ArrayList<>();
        }
    }

    @FXML
    public void btnHelpClicked(ActionEvent e) {
        try {
            final String HELP_SCREEN_FXML = "HelpScreen.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(HELP_SCREEN_FXML));
            HelpScreenController helpScreenController = new HelpScreenController();
            fxmlLoader.setController(helpScreenController);

            Parent root = fxmlLoader.load();

            helpScreenController.setAbout();

            Stage helpMenuStage = new Stage();
            helpMenuStage.initModality(Modality.NONE);
            helpMenuStage.initOwner(((Node) e.getSource()).getScene().getWindow());
            helpMenuStage.setScene(new Scene(root));
            helpMenuStage.setTitle("About");
            helpMenuStage.show();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @FXML
    public void btnQuitClicked(ActionEvent e) {
        int option = JOptionPane.showConfirmDialog(null, "Do you want to exit the program?",
                "Exit the program", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) Platform.exit();
    }
}
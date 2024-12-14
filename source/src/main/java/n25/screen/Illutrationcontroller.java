package screen;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import virus.Virus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IllustrationController {
    @FXML
    private ImageView virus_diagram;
    private Virus virus;
    @FXML
    private TextArea taVirusDescription;
    public IllustrationController(Virus virus) {
        this.virus = virus;
    }
    public void setIllustration() {
        try {
            Image image = new Image(virus.getIllustrationPath());
            virus_diagram.setImage(image);

            String DESCRIPTION_PATH = virus.getIllustrationPath().replace("png", "txt");
            DESCRIPTION_PATH = DESCRIPTION_PATH.replace("jpg", "txt");

            String currentPath = System.getProperty("user.dir");
            DESCRIPTION_PATH = currentPath + "/src/" + DESCRIPTION_PATH;

            String content = new String(Files.readAllBytes(Paths.get(DESCRIPTION_PATH)));
            taVirusDescription.setText(content);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
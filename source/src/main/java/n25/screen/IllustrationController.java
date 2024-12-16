package n25.screen;

import javafx.fxml.FXML;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class IllustrationController {
    @FXML
    private ImageView imageEvel;

    @FXML
    private ImageView imageNonEvel;

    public void setIllustration() {
        try {
            // Tải ảnh "evel.jpg"
            String evelPath = getClass().getResource("evel.jpg").toExternalForm();
            Image evelImage = new Image(evelPath);
            imageEvel.setImage(evelImage);
    
            // Tải ảnh "non-evel.jpg"
            String nonEvelPath = getClass().getResource("non-evel.jpg").toExternalForm();
            Image nonEvelImage = new Image(nonEvelPath);
            imageNonEvel.setImage(nonEvelImage);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    
}
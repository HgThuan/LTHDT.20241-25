package n25.screen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Menu extends Application {

    private static final int WIDTH = 800;  // Chiều rộng cửa sổ
    private static final int HEIGHT = 600; // Chiều cao cửa sổ

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, WIDTH, HEIGHT);
            stage.setScene(scene);
            stage.setOnCloseRequest(event -> {
                event.consume(); // Ngăn chặn thoát ngay lập tức
                quitApplication(stage); // Xử lý logic thoát
            });

            stage.show(); 

        } catch (Exception e) {
            System.err.println("Error loading the menu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void quitApplication(Stage stage) {
        boolean confirm = showConfirmationDialog("Do you really want to quit?");
        if (confirm) {
            stage.close(); 
            System.exit(0); 
        }
    }

    private boolean showConfirmationDialog(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText(null);
        alert.setContentText(message);

        javafx.scene.control.ButtonType result = alert.showAndWait().orElse(javafx.scene.control.ButtonType.CANCEL);
        return result == javafx.scene.control.ButtonType.OK;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

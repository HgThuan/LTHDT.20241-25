module n07 {
    requires javafx.controls;
    requires javafx.fxml;

    opens n07 to javafx.fxml;
    exports n07;
}

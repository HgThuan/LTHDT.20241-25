module n07 {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    
    opens n07 to javafx.fxml;
    exports n07;
}

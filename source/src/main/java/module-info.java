module n07 {
    
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    
    opens n25 to javafx.fxml;
    exports n25;
    exports n25.viruscomponent;
    exports n25.subcomponent;
    exports n25.virus;
}

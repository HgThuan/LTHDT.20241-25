package n25;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Cell extends SubComponent {

    public Cell(Location location, int unitSize, Color color) {
        this.location = location;
        this.unitSize = unitSize;
        this.color = color;
    }

    public void draw(Pane pane) {
        Circle circle = new Circle(location.x, location.y, unitSize * 5);
        circle.setFill(color);
        pane.getChildren().add(circle);
        shapes.add(circle);
    }
}

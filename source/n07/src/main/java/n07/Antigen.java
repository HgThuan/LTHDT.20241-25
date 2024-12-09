package n07;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Antigen extends SubComponent {

    public Antigen(Location location, int unitSize, Color color) {
        this.location = location;
        this.unitSize = unitSize;
        this.color = color;
    }

    public void draw(Pane pane) {
        Circle circle = new Circle(location.x, location.y, unitSize * 1.25);
        circle.setFill(color);
        pane.getChildren().add(circle);
        shapes[0] = circle;
    }
}

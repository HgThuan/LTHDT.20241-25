package n07;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Antigen {
    Shape[] shapes;
    Location location;
    int unitSize;
    Color color;

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

    public void relocate(int direction, int distance) {
        location.moveDirection(direction, distance);
        for (Shape shape : shapes) {
            shape.relocate(location.x, location.y);
        }
    }
}

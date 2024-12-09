package n25;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class SubComponent {
    Location location;
    int unitSize;
    Color color;
    Shape[] shapes;

    public abstract void draw(Pane pane);
    
    public void relocate(int direction, int distance) {
        location.moveDirection(direction, distance);
        for (Shape shape : shapes) {
            shape.relocate(location.x, location.y);
        }
    }
}

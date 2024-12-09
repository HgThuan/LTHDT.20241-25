package n25;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class SubComponent {
    Location location;
    int unitSize;
    Color color;
    List<Shape> shapes = new ArrayList<>();

    public abstract void draw(Pane pane);
    
    public void relocate(int direction, int distance) {
        location.moveDirection(direction, distance);
        for (Shape shape : shapes) {
            shape.relocate(location.x, location.y);
        }
    }
}

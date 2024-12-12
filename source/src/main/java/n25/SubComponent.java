package n25;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class SubComponent {
    protected Location location;
    protected int unitSize;
    protected Color color;
    protected Pane area;
    protected List<Shape> shapes = new ArrayList<>();

    public abstract void draw(Pane area);
    
    public void relocate(Vector_2D vector) {
        location.move(vector);
        for (Shape shape : shapes) {
            shape.relocate(shape.getLayoutX() + vector.x, shape.getLayoutY() + vector.y);
        }
    }

    public void dispose() {
        for (Shape shape : shapes) {
            area.getChildren().remove(shape);
        }
    }
}

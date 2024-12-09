package n25;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public abstract class VirusComponent {
    Location center;
    int radius;
    Pane area;
    List<SubComponent> subComponent = new ArrayList<>();
    List<Shape> shapes = new ArrayList<>();

    public void relocate(Location location)
    {
        center.move(location);
        for (SubComponent sub : subComponent) {
            sub.relocate(Location.subtract(center, sub.location));
        }
        for (Shape shape : shapes) {
            shape.relocate(shape.getLayoutX() + location.x, shape.getLayoutY() + location.y);
        }
    }

    public void relocate(vector2D vector)
    {
        center.move(vector);
        for (SubComponent sub : subComponent) {
            sub.relocate(vector);
        }
        for (Shape shape : shapes) {
            shape.relocate(shape.getLayoutX() + vector.x, shape.getLayoutY() + vector.y);
        }
    }

    public void dispose()
    {
        for (SubComponent sub : subComponent) {
            sub.dispose();
        }
        for (Shape shape : shapes) {
            area.getChildren().remove(shape);
        }
        subComponent.clear();
        shapes.clear();
    }

    public abstract void changeStage(int stage);

    public abstract void draw(Pane area);
}
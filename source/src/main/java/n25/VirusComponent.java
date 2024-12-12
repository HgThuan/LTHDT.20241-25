package n25;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class VirusComponent {
    public static final int CIRCLE_STAGE = 0;
    public static final int HEXAGON_STAGE = 1;

    protected Location center;
    protected int radius;
    protected int unitSize;
    protected Pane area;
    protected List<SubComponent> subComponent = new ArrayList<>();
    protected List<Shape> shapes = new ArrayList<>();
    protected Color color;
    protected Color subColor;

    public VirusComponent(Location center, int radius, int unitSize, Pane area, Color color, Color subColor) {
        this.center = center;
        this.radius = radius;
        this.unitSize = unitSize;
        this.area = area;
        this.color = color;
        this.subColor = subColor;
    }

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

    public void relocate(Vector_2D vector)
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

    protected void draw(Pane area, int componentStyle, int subComponentType)
    {
        this.area = area;
        for (Shape shape : shapes) {
            area.getChildren().remove(shape);
        }
        shapes.clear();
        for (SubComponent sub : this.subComponent) {
            sub.dispose();
        }
        this.subComponent.clear();

        SubComponentType.createSubComponent(ComponentStyle.createComponent(componentStyle, shapes, center, radius, unitSize, color), center, subComponentType, this.subComponent, unitSize, subColor);
        
        if (shapes != null) {
            for (Shape shape : shapes) {
                area.getChildren().add(shape);
            }
        }
        if (subComponent != null) {
            for (SubComponent sub : subComponent) {
                sub.draw(area);
            }
        }
    }
}
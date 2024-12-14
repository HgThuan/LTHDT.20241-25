package n25;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class VirusComponent {
    protected Location center;
    protected int radius;
    protected int unitSize;
    protected Pane area;
    protected List<SubComponent> subComponent = new ArrayList<>();
    protected List<Shape> shapes = new ArrayList<>();
    protected Color color;
    protected Color subColor;
    protected int componentStyle;
    protected int subComponentType;

    public VirusComponent(Location center, int radius, int unitSize, Color color, Color subColor) {
        this.center = center;
        this.radius = radius;
        this.unitSize = unitSize;
        this.color = color;
        this.subColor = subColor;
    }

    public void setArea(Pane area)
    {
        this.area = area;
    }
    
    public void relocate(Location location)
    {
        center.move(location);
    }

    public void relocate(Vector_2D vector)
    {
        center.move(vector);
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

    protected void draw()
    {
        try
        {
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
        finally{}
    }

    protected void draw(Pane area)
    {
        this.area = area;
        draw(); 
    }
}
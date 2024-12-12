package n25;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class Cell {
    private Location location;                          // Vị trí của tế bào
    private int radius;                                 // Bán kính của tế bào
    private int unitSize;                               // Kích thước tế bào
    private Color color;                                // Màu sắc của tế bào
    private Color subColor;                             // Màu sắc của tế bào khi bị nhiễm
    private List<Shape> shapes = new ArrayList<>();     // Danh sách các Shape để vẽ tế bào

    public Cell(Location location, int radius, int unitSize, Color color, Color subColor) {
        this.location = location;
        this.radius = radius;
        this.unitSize = unitSize;
        this.color = color;
        this.subColor = subColor;
    }

    public void draw(Pane area) {
        for (Shape shape : shapes) {
            area.getChildren().remove(shape);
        }
        shapes.clear();

        ComponentStyle.createComponent(ComponentStyle.CIRCLE_STYLE, shapes, location, radius, unitSize, color);
        
        if (shapes.size() > 0) {
            for (Shape shape : shapes) {
                area.getChildren().add(shape);
            }
        }
    }

    public void beInfected() {
        for (Shape shape : shapes) {
            shape.setFill(subColor);
        }
    }

    public void recover() {
        for (Shape shape : shapes) {
            shape.setFill(color);
        }
    }
}

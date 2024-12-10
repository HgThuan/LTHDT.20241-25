package n25;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Glycoprotein extends SubComponent {
    private int direction;
    public Glycoprotein(Location location, int direction, int unitSize, Color color){
        this.location = location;
        this.direction = direction;
        this.unitSize = unitSize;
        this.color = color;
    }

    public void draw(Pane pane){
        Circle circle = new Circle(location.x, location.y, unitSize * 1.25);
        circle.setFill(color);
        pane.getChildren().add(circle);
        shapes.add(circle);
        Rectangle rectangle = new Rectangle(location.x, location.y, unitSize * 1.5, unitSize * 0.5);
        rectangle.setFill(color);
        pane.getChildren().add(rectangle);
        shapes.add(rectangle);
        rotateGlyco(circle, rectangle);
    }

    public void rotateGlyco(Circle circle, Rectangle rectangle){
        double angle = 0;
        switch (direction) {
            case 0: angle = 0; break;      // lên 
            case 1: angle = 90; break;     // sang phải
            case 2: angle = 180; break;    // xuống
            case 3: angle = 270; break;    // sang trái
        }
        circle.setRotate(angle);
        rectangle.setRotate(angle);
    }
}

package n25;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Glycoprotein extends SubComponent {
    private int direction;
    private double rotationAngle;
    public Glycoprotein(Location location, int direction, int unitSize, Color color){
        this.location = location;
        this.direction = direction;
        this.unitSize = unitSize;
        this.color = color;
    }

    public void draw(Pane pane){
        //draw rectangle
        Rectangle rectangle = new Rectangle(location.x, location.y, unitSize * 2, unitSize * 0.5);
        rectangle.setFill(color);
        rectangle.setTranslateX(-rectangle.getWidth()/2);
        rectangle.setTranslateY(-rectangle.getHeight()/2);
        pane.getChildren().add(rectangle);
        shapes.add(rectangle);
        //draw circle
        Circle circle = new Circle(location.x, location.y, unitSize * 1.25);
        circle.setFill(color);
        circle.setTranslateX(-rectangle.getWidth()/2);
        circle.setTranslateY(-rectangle.getHeight()/2);
        pane.getChildren().add(circle);
        shapes.add(circle);

        rotateGlyco(circle, rectangle, rotationAngle);
    }

    public void rotateGlyco(Circle circle, Rectangle rectangle, double angle){
        circle.setRotate(angle);
        rectangle.setRotate(angle);
    }
}

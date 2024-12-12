package n25;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Glycoprotein extends SubComponent {
    private final int direction;
    public Glycoprotein(Location location, int direction, int unitSize, Color color){
        this.location = location;
        this.direction = direction;
        this.unitSize = unitSize;
        this.color = color;
    }
    @Override
    public void draw(Pane area){
        // vẽ Line
        Location start = location;
        Location end = new Location(location.x + (int) (4 * unitSize * Math.cos(Math.toRadians(direction))),
                location.y + (int) (4 * unitSize * Math.sin(Math.toRadians(direction))));
        Line line = new Line(start.x, start.y, end.x, end.y);
        line.setStroke(color);
        line.setStrokeWidth(0.5 * unitSize);
        area.getChildren().add(line);
        shapes.add(line);
        
        //draw circle
        Circle circle = new Circle(end.x, end.y, unitSize * 2);
        circle.setFill(color);
        area.getChildren().add(circle);
        shapes.add(circle);
    }
}

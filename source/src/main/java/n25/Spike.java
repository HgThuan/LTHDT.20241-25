package n25;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Spike extends SubComponent {
    private int direction;
    public Spike(Location location, int direction, int unitSize, Color color){
        this.location = location;
        this.direction = direction;
        this.unitSize = unitSize;
        this.color = color;
    }

    @Override
    public void draw(Pane area){
        this.area = area;
        // vẽ Line
        Location start = location;
        Location end = new Location(location.x + (int) (3 * unitSize * Math.cos(Math.toRadians(direction))),
                location.y + (int) (3 * unitSize * Math.sin(Math.toRadians(direction))));
        Line line = new Line(start.x, start.y, end.x, end.y);
        line.setStroke(color);
        line.setStrokeWidth(unitSize);
        area.getChildren().add(line);
        shapes.add(line);
    }
}

package n25;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class ComponentStyle {
    public static final int CIRCLE_STYLE = 0;
    public static final int HEXAGON_STYLE = 1;

    public static List<Location> createComponent(int componentStyle, List<Shape> shapes, Location center, double radius, int unitSize, Color color) {
        List<Location> result = new ArrayList<>();
        switch (componentStyle) {
            case CIRCLE_STYLE:
                int circleCount = (int) (Math.PI * radius / unitSize);
                for (int i = 0; i < circleCount; i++) {
                    Double angle = 2 * Math.PI * i / circleCount;
                    Double x = center.x + radius * Math.cos(angle);
                    Double y = center.y + radius * Math.sin(angle);
                    Circle circle = new Circle(x, y, unitSize);
                    circle.setFill(color);
                    shapes.add(circle);
                }
                for (int i = 0; i < 40; i++)
                {
                    Location location = new Location(center.x + (int) (radius * Math.cos(Math.toRadians(i * 20))), center.y + (int) (radius * Math.sin(Math.toRadians(i * 20))));
                    result.add(location);
                }
                break;
            case HEXAGON_STYLE:
                int circleCountForHexagon = (int) (radius / (2 * unitSize));
                int angle = 90;
                for (int i = 0; i < 6; i++) {
                    Location start = new Location(center.x + (int) (radius * Math.cos(Math.toRadians(angle))), center.y + (int) (radius * Math.sin(Math.toRadians(angle))));
                    angle += 60;
                    Location end = new Location(center.x + (int) (radius * Math.cos(Math.toRadians(angle))), center.y + (int) (radius * Math.sin(Math.toRadians(angle))));
                    for (int j = 0; j < circleCountForHexagon; j++) {
                        Location drawLocation = new Location(start.x + (end.x - start.x) * j / circleCountForHexagon, start.y + (end.y - start.y) * j / circleCountForHexagon);
                        Circle circle = new Circle(drawLocation.x, drawLocation.y, unitSize);
                        circle.setFill(color);
                        shapes.add(circle);
                    }
                    Location l0 = new Location(start.x, start.y);
                    Location l1 = new Location(start.x + (end.x - start.x) * 1 / 6, start.y + (end.y - start.y) * 1 / 6);
                    Location l2 = new Location(start.x + (end.x - start.x) * 2 / 6, start.y + (end.y - start.y) * 2 / 6);
                    Location l3 = new Location(start.x + (end.x - start.x) * 3 / 6, start.y + (end.y - start.y) * 3 / 6);
                    Location l4 = new Location(start.x + (end.x - start.x) * 4 / 6, start.y + (end.y - start.y) * 4 / 6);
                    Location l5 = new Location(start.x + (end.x - start.x) * 5 / 6, start.y + (end.y - start.y) * 5 / 6);
                    result.add(l0);
                    result.add(l1);
                    result.add(l2);
                    result.add(l3);
                    result.add(l4);
                    result.add(l5);
                }
                break;
            default:
                break;
        }
        return result;
    }
}

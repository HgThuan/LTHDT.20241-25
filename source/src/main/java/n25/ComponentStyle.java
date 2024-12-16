package n25;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class ComponentStyle {
    public static final int CIRCLE_STYLE = 0;
    public static final int HEXAGON_STYLE = 1;
    public static final int SPIRAL_STYLE = 2;

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
                for (int i = 0; i < 30; i++)
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
            case SPIRAL_STYLE:
                // Lấy tọa độ trung tâm từ Location
                double centerX = center.x;
                double centerY = center.y - radius / 2;

                double stepHeight = 2;
                double amplitude = radius / 4.0; // Biên độ của xoắn ốc (độ rộng giữa hai đường xoắn)

                for (int i = 0; i < radius / stepHeight; i++) {
                    // Góc hiện tại (dùng để tính sin/cos cho vị trí xoắn ốc)
                    angle = (int) (i * 720 / radius);

                    // Tính tọa độ cho hai đường xoắn ốc
                    double leftX = centerX - amplitude * Math.cos(Math.toRadians(angle));
                    double leftY = centerY + i * stepHeight;

                    double rightX = centerX + amplitude * Math.cos(Math.toRadians(angle));
                    double rightY = centerY + i * stepHeight;

                    // Vẽ các điểm trên hai đường xoắn ốc
                    Circle leftCircle = new Circle(leftX, leftY, unitSize);
                    leftCircle.setFill(color);
                    shapes.add(leftCircle);

                    Circle rightCircle = new Circle(rightX, rightY, unitSize);
                    rightCircle.setFill(color);
                    shapes.add(rightCircle);

                    // Vẽ các điểm trên hai đường xoắn ốc
                    Circle leftCircleTmp = new Circle(leftX, leftY, unitSize); 
                    leftCircleTmp.setFill(color); 
                    leftCircleTmp.setStroke(null); 
                    leftCircleTmp.setStrokeWidth(1); 
                    shapes.add(leftCircleTmp);

                    Circle rightCircleTmp = new Circle(rightX, rightY, unitSize); 
                    rightCircleTmp.setFill(color); 
                    rightCircleTmp.setStroke(null); 
                    rightCircleTmp.setStrokeWidth(1); 
                    shapes.add(rightCircleTmp);
                
                    if (i % 2 == 0) { 
                        Line basePair = new Line(leftX, leftY, rightX, rightY);
                        basePair.setStroke(Color.RED);
                        basePair.setStrokeWidth(1.5);
                        shapes.add(basePair);
                    }
                }
            default:
                break;
        }
        return result;
    }
}

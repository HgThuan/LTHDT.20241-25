package n25;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Capsit extends VirusComponent {
    public final int CIRCLE_STAGE = 0;
    public final int HEXAGON_STAGE = 1;

    public Capsit(Location center, int radius, int unitSize, Pane area, Color color) {
        this.center = center;
        this.radius = radius;
        this.unitSize = unitSize;
        this.area = area;
        this.color = color;
    }

    public void draw(Pane area) {
        this.area = area;
        for (Shape shape : shapes) {
            area.getChildren().remove(shape);
        }
        shapes.clear();
        for (SubComponent sub : subComponent) {
            sub.dispose();
        }
        subComponent.clear();

        switch (stage) {
            case 0:
                // Tính số lượng hình tròn nhỏ cần thiết để tạo thành hình tròn lớn đại diện cho vỏ capsit
                int circleCount = (int) (Math.PI * radius / unitSize);

                // Vẽ hình tròn lớn đại diện cho vỏ capsit
                for (int i = 0; i < circleCount; i++) {
                    Double angle = 2 * Math.PI * i / circleCount;
                    Double x = center.x + radius * Math.cos(angle);
                    Double y = center.y + radius * Math.sin(angle);
                    Circle circle = new Circle(x, y, unitSize);
                    circle.setFill(color);
                    shapes.add(circle);
                    area.getChildren().add(circle);
                }
                break;
            case 1:
                // Tính số lượng hình tròn cho mỗi cạnh của hình lục giác
                int circleCountForHexagon = (int) (radius / (2 * unitSize));
                // Tạo vector2D để vẽ hình lục giác
                int angle = 90;
                for (int i = 0; i < 6; i++) {
                    Location Start = new Location(center.x + (int) (radius * Math.cos(Math.toRadians(angle))), center.y + (int) (radius * Math.sin(Math.toRadians(angle))));
                    angle += 60;
                    Location End = new Location(center.x + (int) (radius * Math.cos(Math.toRadians(angle))), center.y + (int) (radius * Math.sin(Math.toRadians(angle))));
                    for (int j = 0; j < circleCountForHexagon; j++) {
                        Location drawLocation = new Location(Start.x + (End.x - Start.x) * j / circleCountForHexagon, Start.y + (End.y - Start.y) * j / circleCountForHexagon);
                        Circle circle = new Circle(drawLocation.x, drawLocation.y, unitSize);
                        circle.setFill(color);
                        shapes.add(circle);
                        area.getChildren().add(circle);
                    }
                }
                break;
            default:
                break;
        }    
    }
}
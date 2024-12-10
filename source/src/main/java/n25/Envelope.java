package n25;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Envelope extends VirusComponent {
    public Envelope(Location center, int radius, int unitSize, Pane area, Color color) {
        super(center, radius, unitSize, area, color);
    }

    public void draw(Pane area, int subComponentType)
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
        
        // Tính số lượng hình tròn nhỏ cần thiết để tạo thành hình tròn lớn đại diện cho Envelope
        int circleCount = (int) (Math.PI * radius / unitSize);
        
        // Vẽ hình tròn lớn đại diện cho Envelope
        for (int i = 0; i < circleCount; i++) {
            Double angle = 2 * Math.PI * i / circleCount;
            Double x = center.x + radius * Math.cos(angle);
            Double y = center.y + radius * Math.sin(angle);
            Circle circle = new Circle(x, y, unitSize);
            circle.setFill(color);
            shapes.add(circle);
            area.getChildren().add(circle);
        }
        
        switch (subComponentType) {
            case SubComponentType.ANTIGEN:
                for (int i = 0; i < 20; i++) {
                    Location location = new Location(center.x + (int) (radius * Math.cos(Math.toRadians(i * 20))), center.y + (int) (radius * Math.sin(Math.toRadians(i * 20))));
                    Antigen antigen = new Antigen(location, unitSize, Color.RED);
                    subComponent.add(antigen);
                }
                break;
        
            default:
                break;
        }
        
        if (subComponent != null) {
            for (SubComponent sub : subComponent) {
                sub.draw(area);
            }
        }
    }
}

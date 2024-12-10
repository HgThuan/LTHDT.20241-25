package n25;

import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class MatrixProtein extends VirusComponent {
    
    public MatrixProtein(Location center, int radius, int unitSize, Pane area, Color color) {
        super(center, radius, unitSize, area, color);
    }
    
    public void draw(Pane area, List<SubComponent> subComponents) {
        this.area = area;
        for (Shape shape : shapes) {
            area.getChildren().remove(shape);
        }
        shapes.clear();
        for (SubComponent sub : this.subComponent) {
            sub.dispose();
        }
        this.subComponent.clear();
        
        // Tính số lượng hình tròn nhỏ cần thiết để tạo thành hình tròn lớn đại diện cho Matrix Protein
        int circleCount = (int) (Math.PI * radius / unitSize);
        
        // Vẽ hình tròn lớn đại diện cho Matrix Protein
        for (int i = 0; i < circleCount; i++) {
            Double angle = 2 * Math.PI * i / circleCount;
            Double x = center.x + radius * Math.cos(angle);
            Double y = center.y + radius * Math.sin(angle);
            Circle circle = new Circle(x, y, unitSize);
            circle.setFill(color);
            shapes.add(circle);
            area.getChildren().add(circle);
        }

        if (subComponents != null) {
            for (SubComponent sub : subComponents) {
                subComponent.add(sub);
                sub.draw(area);
            }
        }
    }
}

//Nucleoid: 
package n25;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
public class Nucleoid extends VirusComponent {
    
    public Nucleoid(Location center, int radius, int unitSize, Color color){
        super(center, radius, unitSize, color, Color.BLACK);
    }

    public void draw(Pane area, int subComponentType, int numSteps){
        this.area = area;
        for (Shape shape : shapes) {
            area.getChildren().remove(shape);
        }
        shapes.clear();
        for (SubComponent sub : subComponent) {
            sub.dispose();
        }
        subComponent.clear();
        // Lấy tọa độ trung tâm từ Location
        double centerX = center.x;
        double centerY = center.y;

        double stepHeight = radius / numSteps;
        double amplitude = radius / 4.0; // Biên độ của xoắn ốc (độ rộng giữa hai đường xoắn)

        for (int i = 0; i < numSteps; i++) {
        // Góc hiện tại (dùng để tính sin/cos cho vị trí xoắn ốc)
        double angle = i * 360.0 / numSteps;

        // Tính tọa độ cho hai đường xoắn ốc
        double leftX = centerX - amplitude * Math.cos(Math.toRadians(angle));
        double leftY = centerY + i * stepHeight;

        double rightX = centerX + amplitude * Math.cos(Math.toRadians(angle));
        double rightY = centerY + i * stepHeight;

        // Vẽ các điểm trên hai đường xoắn ốc
        Circle leftCircle = new Circle(leftX, leftY, unitSize);
        leftCircle.setFill(color);
        shapes.add(leftCircle);
        area.getChildren().add(leftCircle);

        Circle rightCircle = new Circle(rightX, rightY, unitSize);
        rightCircle.setFill(color);
        shapes.add(rightCircle);
        area.getChildren().add(rightCircle);
        // Vẽ các điểm trên hai đường xoắn ốc
        Circle leftCircleTmp = new Circle(leftX, leftY, unitSize); 
        leftCircleTmp.setFill(color); 
        leftCircleTmp.setStroke(null); 
        leftCircleTmp.setStrokeWidth(1); 
        shapes.add(leftCircleTmp);
        area.getChildren().add(leftCircleTmp);

        Circle rightCircleTmp = new Circle(rightX, rightY, unitSize); 
        rightCircleTmp.setFill(color); 
        rightCircleTmp.setStroke(null); 
        rightCircleTmp.setStrokeWidth(1); 
        shapes.add(rightCircleTmp);
        area.getChildren().add(rightCircleTmp);
        
        if (i % 2 == 0) { 
        Line basePair = new Line(leftX, leftY, rightX, rightY);
        basePair.setStroke(Color.RED);
        basePair.setStrokeWidth(1.5);
        shapes.add(basePair);
        area.getChildren().add(basePair);
    }
}
        }
}

    



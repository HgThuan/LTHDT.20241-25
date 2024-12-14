//Nucleoid: 
package n25;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
public class Enzyme extends VirusComponent {
    
    public Enzyme(Location center, int radius, int unitSize, Color color, Color subColor){
        super(center, radius, unitSize, color, subColor);
    }

    public void draw(Pane area, int subComponentType) {
        this.area = area;
        for (Shape shape : shapes) {
            area.getChildren().remove(shape);
        }
        shapes.clear();
        for (SubComponent sub : subComponent) {
            sub.dispose();
        }
        subComponent.clear();
        
        double centerX = center.x;
        double centerY = center.y;
    
        double stepHeight = 1.67;
        double amplitude = radius / 4.0; 
        double angle = 0;    
        for (int i = 0; i < radius / stepHeight; i++) {
            // Góc hiện tại (dùng để tính sin/cos cho vị trí xoắn ốc)
            angle = (int) (i * 720 / radius);

            // Tính tọa độ cho hai đường xoắn ốc
            double x = centerX - amplitude * Math.cos(Math.toRadians(angle));
            double y = centerY + i * stepHeight;

            // Vẽ các điểm trên hai đường xoắn ốc
            Circle leftCircle = new Circle(x, y, unitSize);
            leftCircle.setFill(color);
            shapes.add(leftCircle);
            if (i > 0) {
                Line line = new Line(centerX, centerY, x, y);
                line.setStroke(subColor);
                line.setStrokeWidth(1.5);
                shapes.add(line);
                area.getChildren().add(line);
            }
    
            
    
            
        }
    }
    
        }


    



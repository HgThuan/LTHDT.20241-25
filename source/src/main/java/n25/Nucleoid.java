//Nucleoid: 
package n25;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
public class Nucleoid extends VirusComponent {
    
    public Nucleoid(Location center, int radius, int unitSize, Pane area, Color color){
        super(center, radius, unitSize, area, color, Color.BLACK);
    }
    
    public void draw(Pane area, int subComponentType){
        this.area = area;
        for (Shape shape : shapes) {
            area.getChildren().remove(shape);
        }
        shapes.clear();
        for (SubComponent sub : subComponent) {
            sub.dispose();
        }
        subComponent.clear();
    }
    
}


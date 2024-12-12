package n25;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class MatrixProtein extends VirusComponent {
    
    public MatrixProtein(Location center, int radius, int unitSize, Color color, Color subColor) {
        super(center, radius, unitSize, color, subColor);
    }
    
    public void draw(Pane area, int subComponentType) {
        draw(area, ComponentStyle.CIRCLE_STYLE, subComponentType);
    }
}

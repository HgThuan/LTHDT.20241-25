package n25;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class MatrixProtein extends VirusComponent {
    
    public MatrixProtein(Location center, int radius, int unitSize, Pane area, Color color, Color subColor) {
        super(center, radius, unitSize, area, color, subColor);
    }
    
    public void draw(Pane area, int subComponentType) {
        draw(area, ComponentStyle.CIRCLE_STYLE, subComponentType);
    }
}

package n25;

import javafx.scene.paint.Color;

public class MatrixProtein extends VirusComponent {
    
    public MatrixProtein(Location center, int radius, int unitSize, Color color, Color subColor, int subComponentType) {
        super(center, radius, unitSize, color, subColor);
        this.subComponentType = subComponentType;
    }
}

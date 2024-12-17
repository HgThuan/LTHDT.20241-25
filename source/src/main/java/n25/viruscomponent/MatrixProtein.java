package n25.viruscomponent;

import javafx.scene.paint.Color;
import n25.Location;

public class MatrixProtein extends VirusComponent {
    
    public MatrixProtein(Location center, int radius, int unitSize, Color color, Color subColor, int subComponentType) {
        super(center, radius, unitSize, color, subColor);
        this.subComponentType = subComponentType;
    }
}

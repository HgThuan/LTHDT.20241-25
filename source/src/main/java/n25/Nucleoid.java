//Nucleoid: 
package n25;
import javafx.scene.paint.Color;

public class Nucleoid extends VirusComponent {
    public Nucleoid(Location center, int radius, int unitSize, Color color) {
        super(center, radius, unitSize, color, Color.BLACK);
        this.componentStyle = ComponentStyle.DOUBLE_SPIRAL_STYLE;
    }
}

    



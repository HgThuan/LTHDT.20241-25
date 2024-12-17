//Nucleoid: 
package n25.viruscomponent;
import javafx.scene.paint.Color;
import n25.Location;

public class Nucleoid extends VirusComponent {
    public Nucleoid(Location center, int radius, int unitSize, Color color) {
        super(center, radius, unitSize, color, Color.BLACK);
        this.componentStyle = ComponentStyle.DOUBLE_SPIRAL_STYLE;
    }
}

    



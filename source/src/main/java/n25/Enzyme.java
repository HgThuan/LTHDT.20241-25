//Nucleoid: 
package n25;
import javafx.scene.paint.Color;
public class Enzyme extends VirusComponent {
    
    public Enzyme(Location center, int radius, int unitSize, Color color){
        super(center, radius, unitSize, color, Color.BLACK);
        this.componentStyle = ComponentStyle.SINGLE_SPIRAL_STYLE;
    }
 }
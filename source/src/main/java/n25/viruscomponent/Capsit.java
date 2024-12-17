package n25.viruscomponent;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import n25.Location;

public class Capsit extends VirusComponent {

    public Capsit(Location center, int radius, int unitSize, Color color, Color subColor, int componentStyle, int subComponentType) {
        super(center, radius, unitSize, color, subColor);
        this.componentStyle = componentStyle;
        this.subComponentType = subComponentType;
    }

    public void draw(Pane area) {
        super.draw(area);
    }
}
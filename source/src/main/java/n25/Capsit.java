package n25;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Capsit extends VirusComponent {

    public Capsit(Location center, int radius, int unitSize, Pane area, Color color, Color subColor) {
        super(center, radius, unitSize, area, color, subColor);
    }

    public void draw(Pane area, int componentStyle, int subComponentType) {
        super.draw(area, componentStyle, subComponentType);
    }
}
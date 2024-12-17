package n25.viruscomponent;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import n25.Location;

public class Envelope extends VirusComponent {
    public Envelope(Location center, int radius, int unitSize, Color color, Color subColor, int subComponentType) {
        super(center, radius, unitSize, color, subColor);
        this.subComponentType = subComponentType;
    }

    public void draw(Pane area)
    {
        super.draw(area);
    }
}

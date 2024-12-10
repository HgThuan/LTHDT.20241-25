package n25;

import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Envelope extends VirusComponent {
    public Envelope(Location center, int radius, int unitSize, Pane area, Color color) {
        super(center, radius, unitSize, area, color);
    }

    public void draw(Pane area, List<SubComponent> subComponents)
    {

    }
}

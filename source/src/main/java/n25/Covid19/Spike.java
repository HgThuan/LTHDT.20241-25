package n25.Covid19;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import n25.VirusComponent;
import n25.Location;

public class Spike extends VirusComponent {
    public Spike(Location center, int radius, int unitSize, Color color, Color subColor) {
        super(center, radius, unitSize, color, color);
    }

    @Override
    public void draw(Pane area) {
        int spikeCount = 12; // Số lượng gai
        for (int i = 0; i < spikeCount; i++) {
            double angle = 2 * Math.PI * i / spikeCount;
            double x = center.x + radius * Math.cos(angle);
            double y = center.y + radius * Math.sin(angle);

            Circle spike = new Circle(x, y, unitSize / 2, color);
            area.getChildren().add(spike);
        }
    }

    @Override
    public void dispose() {
    }
}

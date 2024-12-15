package n25.Covid19;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import n25.VirusComponent;
import n25.Location;

public class RNA extends VirusComponent {
    public RNA(Location center, int radius, int unitSize, Color color, Color subColor) {
        super(center, radius, unitSize, color, color);
    }

    @Override
    public void draw(Pane area) {
        Polyline rna = new Polyline();
        for (int i = 0; i < 360; i += 10) {
            double x = center.x + radius * Math.cos(Math.toRadians(i));
            double y = center.y + radius * Math.sin(Math.toRadians(i)) * 0.5; // Tạo hiệu ứng xoắn
            rna.getPoints().addAll(x, y);
        }
        rna.setStroke(color);
        area.getChildren().add(rna);
    }

    @Override
    public void dispose() {
    }
}


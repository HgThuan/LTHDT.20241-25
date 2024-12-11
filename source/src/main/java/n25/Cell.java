package n25;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Cell {
    private Location location;      // Vị trí của tế bào
    private int unitSize;           // Kích thước tế bào
    private Color color;            // Màu sắc của tế bào
    private boolean infected;       // Trạng thái tế bào bị nhiễm hay chưa
    private Circle cellShape;       // Đối tượng hình tròn đại diện cho tế bào
    private Text statusText;        // Hiển thị trạng thái của tế bào

    public Cell(Location location, int unitSize, Color color) {
        this.location = location;
        this.unitSize = unitSize;
        this.color = color;
        this.infected = false;
    }

    public void draw(Pane pane) {
        cellShape = new Circle(location.x, location.y, unitSize * 5);
        cellShape.setFill(color);
        cellShape.setStroke(Color.BLACK); // set màu viền
        cellShape.setStrokeWidth(2);

        statusText = new Text(location.x - unitSize, location.y - unitSize - 10, "Healthy");
        statusText.setFill(Color.BLACK);

        pane.getChildren().addAll(cellShape, statusText);
    }

    public void showInfection(Pane pane, Virus virus) {
        if (infected) {
            cellShape.setFill(Color.RED);
            statusText.setText("Infected by " + virus.getName());
        } 
    }
}

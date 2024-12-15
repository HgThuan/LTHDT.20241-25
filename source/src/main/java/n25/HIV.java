package n25;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class HIV extends Virus {
    private final int TIME = 5000;
    public HIV(String name, Location center, int radius, int unitSize)
    {
        this.name = name;
        this.center = center;
        this.radius = radius;
        this.unitSize = unitSize;

        List<VirusComponent> components = List.of(
            new Nucleoid(center.clone(), radius / 2, unitSize, Color.GREEN),
            new Capsit(center.clone(), radius, unitSize, Color.GOLD, Color.BLUE, ComponentStyle.HEXAGON_STYLE, SubComponentType.ANTIGEN),
            new MatrixProtein(center.clone(), radius + radius / 2, unitSize, Color.RED, Color.BLUE,  SubComponentType.NONE),
            new Envelope(center.clone(), radius + radius, unitSize, Color.YELLOW, Color.RED, SubComponentType.GLYCOPROTEIN)
        );
        VirusStructure virusStructure = new VirusStructure(components);
        this.virusStructure = virusStructure;
    }

    private int angle;
    private List<HIV> hivs = new ArrayList<>();
    private List<Vector_2D> speeds = new ArrayList<>();
    private List<Nucleoid> nucleoids = new ArrayList<>();
    @Override
    public void displayInfection(Pane area, int timeSleep) {
        virusStructure.draw(area);
        Location cellLocation = new Location(center.x + radius * 8, center.y);
        Cell cell = new Cell(cellLocation, radius * 5, 5, Color.LIGHTBLUE, Color.BLACK);
        cell.draw(area);
        // Thiết lập các giai đoạn 
        // Giai đoạn 1:
        // Virus xâm nhập vào tế bào
        Vector_2D speed = new Vector_2D(5 * radius * timeSleep / TIME, 0);
        Timeline getIn = new Timeline(new KeyFrame(Duration.millis(timeSleep), e -> 
        {
            virusStructure.relocate(speed);
        }));
        getIn.setCycleCount(TIME / timeSleep);
        
        // Giai đoạn 2:
        // Virus tấn công tế bào
        angle = 180;
        Timeline attack = new Timeline(new KeyFrame(Duration.millis(TIME / 8), e -> 
        {
            Location nucleusLocation = new Location(cellLocation.x + (int) (2 * radius * Math.cos(Math.toRadians(angle))), cellLocation.y + (int) (2 * radius * Math.sin(Math.toRadians(angle))));
            Nucleoid nucleus = new Nucleoid(nucleusLocation, radius / 2, unitSize, Color.GREEN);
            nucleus.draw(area);
            nucleoids.add(nucleus);
            angle += 45;
        }));
        attack.setCycleCount(8);

        // Giai đoạn 3:
        // Virus thoát khỏi tế bào
        // Khởi tạo speeds
        for (int i = 0; i < 8; i++)
        {
            Vector_2D speedHIV = new Vector_2D((int) (3 * radius * timeSleep / TIME * Math.cos(Math.toRadians(180 + i * 45))), (int) (3 * radius * timeSleep / TIME * Math.sin(Math.toRadians(180 + i * 45))));
            speeds.add(speedHIV);
        }
        Timeline getOut = new Timeline(new KeyFrame(Duration.millis(timeSleep), e -> 
        {
            for (int i = 0; i < 8; i++)
            {
                hivs.get(i).virusStructure.relocate(speeds.get(i));
            }
        }));
        getOut.setCycleCount(TIME / timeSleep);

        // Thực thi các giai đoạn
        // Giai đoạn 1:
        getIn.play();

        // Giai đoạn 2:
        angle = 216;
        getIn.setOnFinished(e -> 
        {
            virusStructure.components.get(0).dispose();
            virusStructure.components.get(1).dispose();
            virusStructure.components.get(2).dispose();
            virusStructure.components.get(3).dispose();
            attack.play();
        });

        // Giai đoạn 3:
        attack.setOnFinished(e -> 
        {
            cell.dispose();
            nucleoids.forEach(nucleoid -> nucleoid.dispose());
            nucleoids.clear();
            hivs.clear();
            hivs.add(this);
            for (int i = 1; i < 8; i++)
            {
                Location newHIVCenter = new Location(cellLocation.x + (int) (2 * radius * Math.cos(Math.toRadians(180 + i * 45))), cellLocation.y + (int) (2 * radius * Math.sin(Math.toRadians(180 + i * 45))));
                HIV hiv = new HIV("HIV", newHIVCenter, radius, unitSize);
                hiv.displayStructure(area);
                hivs.add(hiv);
            }
            getOut.play();
        });
    }
}

package n25.Covid19;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import n25.Location;
import n25.Vector_2D;
import n25.Virus;
import n25.VirusComponent;
import n25.VirusStructure;
import n25.Capsit;
import n25.Cell;
import n25.ComponentStyle;
import n25.Envelope;
import n25.Covid19.RNA;
import n25.Covid19.Spike;
public class SARSCoV2 extends Virus {
    private final int TIME = 5000;

    public SARSCoV2(String name, Location center, int radius, int unitSize) {
        this.name = name;
        this.center = center;
        this.radius = radius;
        this.unitSize = unitSize;

        // Cấu trúc của SARS-CoV-2
        List<VirusComponent> components = List.of(
            new Spike(center.clone(), radius, unitSize, Color.RED),  // Protein Spike
            new Envelope(center.clone(), radius, unitSize, Color.ORANGE, Color.LIGHTGRAY, 1), // Vỏ Lipid
            new Capsit(center.clone(), radius / 2, unitSize, Color.YELLOW, Color.BLUE, ComponentStyle.HEXAGON_STYLE, 1), // Capsit
            new RNA(center.clone(), radius / 4, unitSize, Color.BLUE) // RNA
        );
        VirusStructure virusStructure = new VirusStructure(components);
        this.virusStructure = virusStructure;
    }

    private List<SARSCoV2> newViruses = new ArrayList<>();
    private List<Vector_2D> speeds = new ArrayList<>();

    @Override
    public void displayInfection(Pane area, int timeSleep) {
        virusStructure.draw(area);

        // Tạo tế bào mục tiêu
        Location cellLocation = new Location(center.x + radius * 7, center.y);
        Cell targetCell = new Cell(cellLocation, radius * 3, 5, Color.LIGHTBLUE, Color.BLACK);
        targetCell.draw(area);

        // Giai đoạn 1: Bám dính (Protein Spike gắn vào thụ thể ACE2)
        Vector_2D moveToCell = new Vector_2D(5 * radius * timeSleep / TIME, 0);
        Timeline attach = new Timeline(new KeyFrame(Duration.millis(timeSleep), e -> {
            virusStructure.relocate(moveToCell);
        }));
        attach.setCycleCount(TIME / timeSleep);

        // Giai đoạn 2: Xâm nhập và giải phóng RNA
        Timeline releaseRNA = new Timeline(new KeyFrame(Duration.millis(TIME / 5), e -> {
            RNA rna = new RNA(center.clone(), radius / 4, unitSize, Color.BLUE);
            rna.draw(area);
        }));
        releaseRNA.setCycleCount(1);

        // Giai đoạn 3: Nhân bản
        Timeline replication = new Timeline(new KeyFrame(Duration.millis(TIME / 5), e -> {
            for (int i = 0; i < 6; i++) {
                Location newVirusLocation = new Location(
                    cellLocation.x + (int) (radius * 2 * Math.cos(Math.toRadians(i * 60))),
                    cellLocation.y + (int) (radius * 2 * Math.sin(Math.toRadians(i * 60)))
                );
                SARSCoV2 newVirus = new SARSCoV2("SARS-CoV-2", newVirusLocation, radius, unitSize);
                newVirus.displayStructure(area);
                newViruses.add(newVirus);
            }
        }));
        replication.setCycleCount(1);

        // Giai đoạn 4: Giải phóng các virus mới
        for (int i = 0; i < 6; i++) {
            Vector_2D speed = new Vector_2D(
                (int) (2 * radius * timeSleep / TIME * Math.cos(Math.toRadians(i * 60))),
                (int) (2 * radius * timeSleep / TIME * Math.sin(Math.toRadians(i * 60)))
            );
            speeds.add(speed);
        }
        Timeline releaseViruses = new Timeline(new KeyFrame(Duration.millis(timeSleep), e -> {
            for (int i = 0; i < newViruses.size(); i++) {
                newViruses.get(i).virusStructure.relocate(speeds.get(i));
            }
        }));
        releaseViruses.setCycleCount(TIME / timeSleep);

       
    }
}

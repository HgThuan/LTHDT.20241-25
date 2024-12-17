package n25;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class SarCoV2 extends Virus {
    public final int TIME = 5000;
    public SarCoV2(String name, Location center, int radius, int unitSize) {
        this.name = name;
        this.radius = radius;
        this.unitSize = unitSize;

        List<VirusComponent> components = List.of(
            new Nucleoid(center.clone(), radius / 2, unitSize, Color.GREEN),
            new Capsit(center.clone(), radius, unitSize, Color.GOLD, Color.BLUE, ComponentStyle.HEXAGON_STYLE, SubComponentType.ANTIGENORENZYME),
            new Envelope(center.clone(), (int) (radius * 1.5), unitSize, Color.YELLOW, Color.RED, SubComponentType.GLYCOPROTEINANDSPIKE)
        );
        VirusStructure virusStructure = new VirusStructure(components, center);
        this.virusStructure = virusStructure;
    }
    
    private int angle;
    private List<SarCoV2> sarCoV2s = new ArrayList<>();
    private List<Vector_2D> speeds = new ArrayList<>();
    private List<Nucleoid> nucleoids = new ArrayList<>();

    // Các biến dùng để vẽ các thành phần của virus
    private List<Shape> shapes = new ArrayList<>();
    private int count;
    private Location startLocation, endLocation;
    private Vector_2D drawVector;
    private List<Location> baseLocations = new ArrayList<>();

    public void displayInfection(Pane area, int timeSleep){
        virusStructure.draw(area);
        Location cellLocation = new Location(virusStructure.getCenter().x + radius * 8, virusStructure.getCenter().y);
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
        // Virus tổng hợp nucleoid
        Timeline synthesis = new Timeline(new KeyFrame(Duration.millis(TIME / 8), e -> 
        {
            Location nucleusLocation = new Location(cellLocation.x + (int) ((3) * radius * Math.cos(Math.toRadians(angle))), cellLocation.y + (int) ((3) * radius * Math.sin(Math.toRadians(angle))));
            baseLocations.add(nucleusLocation);
            Nucleoid nucleus = new Nucleoid(nucleusLocation, radius / 2, unitSize, Color.GREEN);
            nucleus.draw(area);
            nucleoids.add(nucleus);
            angle += 90;
        }));
        synthesis.setCycleCount(3);

        // Giai đoạn 3: 
        // Virus hoàn thiện các thành phần khác
        // Tạo vỏ capsit
        int circleCountForHexagon = (int) (radius / (2 * unitSize));
        Timeline createCapsit = new Timeline(new KeyFrame(Duration.millis(timeSleep), e -> 
        {
            if (count == circleCountForHexagon)
            {
                count = 0;
                angle += 60;
                startLocation = endLocation.clone();
                endLocation = new Location((int) (radius * Math.cos(Math.toRadians(angle))), (int) (radius * Math.sin(Math.toRadians(angle))));
            }
            drawVector = new Vector_2D(startLocation.x + (endLocation.x - startLocation.x) * count / circleCountForHexagon, startLocation.y + (endLocation.y - startLocation.y) * count / circleCountForHexagon);
            for (Location baseLocation : baseLocations)
            {
                Location newLocation = baseLocation.add(drawVector);
                Circle circle = new Circle(newLocation.x, newLocation.y, unitSize);
                circle.setFill(Color.GOLD);
                shapes.add(circle);
                area.getChildren().add(circle);
            }
            count++;
        }));
        createCapsit.setCycleCount(6 * circleCountForHexagon);

        // Giai đoạn 4:
        // Tạo các thành phần phụ của virus
        // Tạo antigen
        Timeline createAntigen = new Timeline(new KeyFrame(Duration.millis(timeSleep), e -> 
        {
            if (count == 3)
            {
                count = 0;
                angle += 60;
                startLocation = endLocation.clone();
                endLocation = new Location((int) (radius * Math.cos(Math.toRadians(angle))), (int) (radius * Math.sin(Math.toRadians(angle))));
            }
            drawVector = new Vector_2D(startLocation.x + (endLocation.x - startLocation.x) * count / 3, startLocation.y + (endLocation.y - startLocation.y) * count / 3);
            for (Location baseLocation : baseLocations)
            {
                Location newLocation = baseLocation.add(drawVector);
                Circle circle = new Circle(newLocation.x, newLocation.y, 2 * unitSize);
                circle.setFill(Color.BLUE);  
                shapes.add(circle);
                area.getChildren().add(circle);
            }
            count++;
        }));
        createAntigen.setCycleCount(18);

        // Giai đoạn 5:
        // Tạo Envelope
        int circleCountForEnvelope = (int) (1.5 * Math.PI * radius / unitSize);
        Timeline createEnvelope = new Timeline(new KeyFrame(Duration.millis(timeSleep / 2), e -> {
            // Góc hiện tại dựa trên bộ đếm
            double angle = 2 * Math.PI * count / circleCountForEnvelope;
        
            // Vector để tính tọa độ điểm hiện tại trên vòng tròn
            drawVector = new Vector_2D(
                (int) (1.5 * radius * Math.cos(angle)), // Xét theo trục X
                (int) (1.5 * radius * Math.sin(angle))  // Xét theo trục Y
            );
        
            // Vẽ từng điểm trên vòng tròn với baseLocations làm tâm
            for (Location baseLocation : baseLocations) {
                Location newLocation = baseLocation.add(drawVector);
                Circle circle = new Circle(newLocation.x, newLocation.y, 1 * unitSize); // Bán kính điểm là 2 * unitSize
                circle.setFill(Color.YELLOW); // Màu sắc của điểm
                shapes.add(circle);
                area.getChildren().add(circle);
            }
        
            // Tăng bộ đếm để vẽ điểm tiếp theo
            count++;
        }));
        createEnvelope.setCycleCount(circleCountForEnvelope);

        // Giai đoạn 6:
        // Tạo các thành phần phụ của virus
        // Tạo glycoprotein và spike
        Timeline createGlycoProteinAndSpike = new Timeline(new KeyFrame(Duration.millis(timeSleep), e -> 
        {
            double angle = 2 * Math.PI * count / 30;
            drawVector = new Vector_2D(
                (int) (1.5 * radius * Math.cos(angle)), // Xét theo trục X
                (int) (1.5 * radius * Math.sin(angle))  // Xét theo trục Y
            );

            for (Location baseLocation : baseLocations) {
                Location newLocation = baseLocation.add(drawVector);
                if (count % 2 == 0)
                {
                    Glycoprotein glycoProtein = new Glycoprotein(
                        newLocation,
                        (int)(count * 12), 
                        unitSize, 
                        Color.RED
                    );
                    glycoProtein.draw(area);
                    shapes.addAll(glycoProtein.shapes);
                }
                else
                {
                    Spike spike = new Spike(
                        newLocation,
                        (int)(count * 12), 
                        unitSize, 
                        Color.RED
                    );
                    spike.draw(area);
                    shapes.addAll(spike.shapes);
                }
            }
            count++;
        }));
        createGlycoProteinAndSpike.setCycleCount(30);
        
        // Giai đoạn 7:
        // Virus thoát khỏi tế bào
        // Khởi tạo speeds
        for (int i = 0; i < 4; i++)
        {
            Vector_2D speedHIV = new Vector_2D((int) (3 * radius * timeSleep / TIME * Math.cos(Math.toRadians(180 + i * 90))), (int) (3 * radius * timeSleep / TIME * Math.sin(Math.toRadians(180 + i * 90))));
            speeds.add(speedHIV);
        }
        Timeline getOut = new Timeline(new KeyFrame(Duration.millis(timeSleep), e -> 
        {
            for (int i = 0; i < 4; i++)
            {
                sarCoV2s.get(i).virusStructure.relocate(speeds.get(i));
            }
        }));
        getOut.setCycleCount(TIME / timeSleep);

        //--------------------------------------------------------------------------------
        // Thực thi các giai đoạn
        // Giai đoạn 1:
        getIn.play();

        // Giai đoạn 2:
        angle = 270;
        getIn.setOnFinished(e -> 
        {
            virusStructure.components.get(1).dispose(); 
            virusStructure.components.get(2).dispose();
            baseLocations.clear();
            baseLocations.add(virusStructure.getCenter());
            synthesis.play();
        });

        // Giai đoạn 3:
        synthesis.setOnFinished( e -> 
        {
            count = 0;
            angle = -30;
            startLocation = new Location(0, -radius);
            endLocation = new Location((int) (radius * Math.cos(Math.toRadians(angle))), (int) (radius * Math.sin(Math.toRadians(angle))));
            createCapsit.play();
        });
        
        // Giai đoạn 4:
        createCapsit.setOnFinished(e -> 
        {
            count = 0;
            angle = -30;
            startLocation = new Location(0, -radius);
            endLocation = new Location((int) (radius * Math.cos(Math.toRadians(angle))), (int) (radius * Math.sin(Math.toRadians(angle))));
            createAntigen.play();
        });

        // Giai đoạn 5: 
        createAntigen.setOnFinished(e ->{
            count = 0;
            angle = 0;
            startLocation = new Location(0, -radius);
            endLocation = new Location((int) (radius * Math.cos(Math.toRadians(angle))), (int) (radius * Math.sin(Math.toRadians(angle))));
            createEnvelope.play();
        });

        // Giai đoạn 6:
        createEnvelope.setOnFinished(e ->{
            count = 0;
            angle = 0;
            startLocation = new Location(0, -radius);
            endLocation = new Location((int) (radius * Math.cos(Math.toRadians(angle))), (int) (radius * Math.sin(Math.toRadians(angle))));
            createGlycoProteinAndSpike.play();
        });

        // Giai đoạn 7:
        createGlycoProteinAndSpike.setOnFinished(e -> 
        {
            shapes.forEach(shape -> area.getChildren().remove(shape));
            shapes.clear();
            cell.dispose();
            nucleoids.forEach(nucleoid -> nucleoid.dispose());
            nucleoids.clear();
            shapes.forEach(shape -> area.getChildren().remove(shape));
            shapes.clear();
            sarCoV2s.clear();
            sarCoV2s.add(this);
            for (int i = 1; i < 4; i++)
            {
                SarCoV2 sarCoV2 = new SarCoV2("SarCoV2", baseLocations.get(i), radius, unitSize);
                sarCoV2.displayStructure(area);
                sarCoV2s.add(sarCoV2);
            }
            getOut.play();
        });
    }
}

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

public class HIV extends Virus {
    private final int TIME = 5000;
    public HIV(String name, Location center, int radius, int unitSize)
    {
        this.name = name;
        this.radius = radius;
        this.unitSize = unitSize;

        List<Location> enzymeLocations = new ArrayList<>();
        enzymeLocations.add(center.clone());
        enzymeLocations.add(center.clone());
        enzymeLocations.get(0).move(new Vector_2D(-radius / 3, 0));
        enzymeLocations.get(1).move(new Vector_2D(radius / 3, 0));

        List<VirusComponent> components = List.of(
            new MatrixProtein(center.clone(), (int) (1.35 * radius), unitSize, Color.RED, Color.BLUE, 0),
            new Nucleoid(center.clone(), radius / 2, unitSize, Color.GREEN),
            new Capsit(center.clone(), radius, unitSize, Color.GOLD, Color.BLUE, ComponentStyle.HEXAGON_STYLE, SubComponentType.ANTIGEN),
            new Envelope(center.clone(), (int) (1.5 * radius), unitSize, Color.YELLOW, Color.RED, SubComponentType.GLYCOPROTEIN),
            new Enzyme(enzymeLocations.get(0), radius / 3, unitSize, Color.BROWN),
            new Enzyme(enzymeLocations.get(1), radius / 3, unitSize, Color.BROWN)
        );
        VirusStructure virusStructure = new VirusStructure(components, center);
        this.virusStructure = virusStructure;
    }

    private int angle;
    private List<HIV> hivs = new ArrayList<>();
    private List<Vector_2D> speeds = new ArrayList<>();
    private List<Nucleoid> nucleoids = new ArrayList<>();
    private List<Enzyme> enzymes = new ArrayList<>();

    // Các biến dùng để vẽ các thành phần của virus
    List<Shape> shapes = new ArrayList<>();
    int circleCountForHexagon;
    int count;
    Location startLocation, endLocation;
    Vector_2D drawVector;
    List<Location> baseLocations = new ArrayList<>();
    @Override
    public void displayInfection(Pane area, int timeSleep) {
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

            Location enzymeLocation1 = nucleusLocation.clone();
            enzymeLocation1.move(new Vector_2D(-radius / 3, 0));
            Enzyme enzyme1 = new Enzyme(enzymeLocation1, radius / 3, unitSize, Color.BROWN);
            enzyme1.draw(area);
            enzymes.add(enzyme1);
            
            Location enzumeLocation2 = nucleusLocation.clone();
            enzumeLocation2.move(new Vector_2D(radius / 3, 0));
            Enzyme enzyme2 = new Enzyme(enzumeLocation2, radius / 3, unitSize, Color.BROWN);
            enzymes.add(enzyme2);
            enzyme2.draw(area);
            angle += 90;
        }));
        synthesis.setCycleCount(3);

        // Giai đoạn 3: 
        // Virus hoàn thiện các thành phần khác
        // Tạo vỏ capsit
        circleCountForHexagon = (int) (radius / (2 * unitSize));
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
        // Tạo MatrixProtein
        int circleCount = (int) (1.35 * Math.PI * radius /unitSize);
        Timeline createMatrixProtein = new Timeline(new KeyFrame(Duration.millis(timeSleep / 2), e -> {
            // Góc hiện tại dựa trên bộ đếm
            double angle = 2 * Math.PI * count / circleCount;
        
            // Vector để tính tọa độ điểm hiện tại trên vòng tròn
            drawVector = new Vector_2D(
                (int) (1.35 * radius * Math.cos(angle)), // Xét theo trục X
                (int) (1.35 * radius * Math.sin(angle))  // Xét theo trục Y
            );
        
            // Vẽ từng điểm trên vòng tròn với baseLocations làm tâm
            for (Location baseLocation : baseLocations) {
                Location newLocation = baseLocation.add(drawVector);
                Circle circle = new Circle(newLocation.x, newLocation.y, unitSize); 
                circle.setFill(Color.RED); // Màu sắc của điểm
                shapes.add(circle);
                area.getChildren().add(circle);
            }
        
            // Tăng bộ đếm để vẽ điểm tiếp theo
            count++;
        }));
        createMatrixProtein.setCycleCount(circleCount);

        // Giai đoạn 6: 
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

        // Giai đoạn 7:
        // Tạo các thành phần phụ của virus
        // Tạo glycoprotein
        Timeline createGlycoProtein = new Timeline(new KeyFrame(Duration.millis(timeSleep), e -> 
        {
            double angle = 2 * Math.PI * count / 15;
            drawVector = new Vector_2D(
                (int) (1.5 * radius * Math.cos(angle)), // Xét theo trục X
                (int) (1.5 * radius * Math.sin(angle))  // Xét theo trục Y
            );

            for (Location baseLocation : baseLocations) {
                Location newLocation = baseLocation.add(drawVector);
                
                Glycoprotein glycoProtein = new Glycoprotein(
                    newLocation,
                    (int)(angle * 180 / Math.PI), 
                    unitSize, 
                    Color.RED
                );
                glycoProtein.draw(area);
                shapes.addAll(glycoProtein.shapes);
            }
            count++;
        }));
        createGlycoProtein.setCycleCount(15);
        
        // Giai đoạn 8:
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
                hivs.get(i).virusStructure.relocate(speeds.get(i));
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
            virusStructure.components.get(3).dispose();
            virusStructure.components.get(0).dispose();
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
            createMatrixProtein.play();
        });
        createMatrixProtein.setOnFinished(e ->{
            count = 0;
            angle = 0;
            startLocation = new Location(0, -radius);
            endLocation = new Location((int) (radius * Math.cos(Math.toRadians(angle))), (int) (radius * Math.sin(Math.toRadians(angle))));
            createEnvelope.play();
        });
        createEnvelope.setOnFinished(e ->{
            count = 0;
            angle = 0;
            startLocation = new Location(0, -radius);
            endLocation = new Location((int) (radius * Math.cos(Math.toRadians(angle))), (int) (radius * Math.sin(Math.toRadians(angle))));
            createGlycoProtein.play();
        });
        createGlycoProtein.setOnFinished(e -> 
        {
            shapes.forEach(shape -> area.getChildren().remove(shape));
            shapes.clear();
            cell.dispose();
            nucleoids.forEach(nucleoid -> nucleoid.dispose());
            nucleoids.clear();
            enzymes.forEach(enzyme -> enzyme.dispose());
            enzymes.clear();
            shapes.forEach(shape -> area.getChildren().remove(shape));
            shapes.clear();
            hivs.clear();
            hivs.add(this);
            for (int i = 1; i < 4; i++)
            {
                HIV hiv = new HIV("HIV", baseLocations.get(i), radius, unitSize);
                hiv.displayStructure(area);
                hivs.add(hiv);
            }
            getOut.play();
        });
    }
}

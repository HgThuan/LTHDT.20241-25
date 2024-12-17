package n25.virus;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import n25.Cell;
import n25.Location;
import n25.Vector_2D;
import n25.VirusStructure;
import n25.subcomponent.Glycoprotein;
import n25.subcomponent.SubComponentType;
import n25.viruscomponent.Capsit;
import n25.viruscomponent.ComponentStyle;
import n25.viruscomponent.Envelope;
import n25.viruscomponent.Enzyme;
import n25.viruscomponent.MatrixProtein;
import n25.viruscomponent.Nucleoid;
import n25.viruscomponent.VirusComponent;

public class HIV extends Virus {
    private final int TIME = 5000;
    public HIV(String name, Location center, int radius, int unitSize)
    {
        this.isEnvelopedVirus = true;
        this.name = name;
        this.radius = radius;
        this.unitSize = unitSize;
        List<Location> enzymeLocations = new ArrayList<>();
        enzymeLocations.add(center.clone());
        enzymeLocations.add(center.clone());
        enzymeLocations.get(0).move(new Vector_2D(-radius / 3, 0));
        enzymeLocations.get(1).move(new Vector_2D(radius / 3, 0));

        List<VirusComponent> components = List.of(
            new MatrixProtein(center.clone(), (int) (1.35 * radius), unitSize, Color.BROWN, Color.BLUE, 0),
            new Nucleoid(center.clone(), radius / 2, unitSize, Color.RED),
            new Capsit(center.clone(), radius, unitSize, Color.GOLD, Color.BLUE, ComponentStyle.HEXAGON_STYLE, SubComponentType.ANTIGEN),
            new Envelope(center.clone(), (int) (1.5 * radius), unitSize, Color.GREEN, Color.RED, SubComponentType.GLYCOPROTEIN),
            new Enzyme(enzymeLocations.get(0), radius / 3, unitSize, Color.GREEN),
            new Enzyme(enzymeLocations.get(1), radius / 3, unitSize, Color.GREEN)
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
        periods.add(new Timeline (new KeyFrame(Duration.millis(timeSleep), e -> 
        {
            virusStructure.relocate(speed);
        })));
        periods.get(0).setCycleCount(TIME / timeSleep);
        
        // Giai đoạn 2:
        // Virus tổng hợp nucleoid
        periods.add(new Timeline(new KeyFrame(Duration.millis(TIME / 8), e -> 
        {
            Location nucleusLocation = new Location(cellLocation.x + (int) ((3) * radius * Math.cos(Math.toRadians(angle))), cellLocation.y + (int) ((3) * radius * Math.sin(Math.toRadians(angle))));
            baseLocations.add(nucleusLocation);
            Nucleoid nucleoid = new Nucleoid(nucleusLocation, radius / 2, unitSize, Color.RED);
            nucleoid.draw(area);
            nucleoids.add(nucleoid);

            Location enzymeLocation1 = nucleusLocation.clone();
            enzymeLocation1.move(new Vector_2D(-radius / 3, 0));
            Enzyme enzyme1 = new Enzyme(enzymeLocation1, radius / 3, unitSize, Color.GREEN);
            enzyme1.draw(area);
            enzymes.add(enzyme1);
            
            Location enzumeLocation2 = nucleusLocation.clone();
            enzumeLocation2.move(new Vector_2D(radius / 3, 0));
            Enzyme enzyme2 = new Enzyme(enzumeLocation2, radius / 3, unitSize, Color.GREEN);
            enzymes.add(enzyme2);
            enzyme2.draw(area);
            angle += 90;
        })));
        periods.get(1).setCycleCount(3);

        // Giai đoạn 3: 
        // Virus hoàn thiện các thành phần khác
        // Tạo vỏ capsit
        circleCountForHexagon = (int) (radius / (2 * unitSize));
        periods.add(new Timeline(new KeyFrame(Duration.millis(timeSleep), e -> 
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
        })));
        periods.get(2).setCycleCount(6 * circleCountForHexagon);

        // Giai đoạn 4:
        // Tạo các thành phần phụ của virus
        // Tạo antigen
        periods.add(new Timeline(new KeyFrame(Duration.millis(timeSleep), e -> 
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
        })));
        periods.get(3).setCycleCount(18);

        // Giai đoạn 5: 
        // Tạo MatrixProtein
        int circleCount = (int) (1.35 * Math.PI * radius /unitSize);
        periods.add(new Timeline(new KeyFrame(Duration.millis(timeSleep/2), e -> {
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
                circle.setFill(Color.BROWN); // Màu sắc của điểm
                shapes.add(circle);
                area.getChildren().add(circle);
            }
        
            // Tăng bộ đếm để vẽ điểm tiếp theo
            count++;
        })));
        periods.get(4).setCycleCount(circleCount);

        // Giai đoạn 6: 
        // Tạo Envelope
        int circleCountForEnvelope = (int) (1.5 * Math.PI * radius / unitSize);
        periods.add(new Timeline(new KeyFrame(Duration.millis(timeSleep/2), e -> {
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
                circle.setFill(Color.GREEN); // Màu sắc của điểm
                shapes.add(circle);
                area.getChildren().add(circle);
            }
        
            // Tăng bộ đếm để vẽ điểm tiếp theo
            count++;
        })));
        periods.get(5).setCycleCount(circleCountForEnvelope);

        // Giai đoạn 7:
        // Tạo các thành phần phụ của virus
        // Tạo glycoprotein
        periods.add(new Timeline(new KeyFrame(Duration.millis(timeSleep), e -> {
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
        }})));
        periods.get(6).setCycleCount(15);
        
        // Giai đoạn 8:
        // Virus thoát khỏi tế bào
        // Khởi tạo speeds
        for (int i = 0; i < 4; i++)
        {
            Vector_2D speedHIV = new Vector_2D((int) (3 * radius * timeSleep / TIME * Math.cos(Math.toRadians(180 + i * 90))), (int) (3 * radius * timeSleep / TIME * Math.sin(Math.toRadians(180 + i * 90))));
            speeds.add(speedHIV);
        }
        periods.add(new Timeline(new KeyFrame(Duration.millis(timeSleep), e -> {
            {
            for (int i = 0; i < 4; i++)
            {
                hivs.get(i).virusStructure.relocate(speeds.get(i));
            }
        }})));
        periods.get(7).setCycleCount(TIME / timeSleep);

        //--------------------------------------------------------------------------------
        // Thực thi các giai đoạn
        // Giai đoạn 1:
        periods.get(0).play();

        // Giai đoạn 2:
        angle = 270;
        periods.get(0).setOnFinished(e -> 
        {
            virusStructure.components.get(3).dispose();
            virusStructure.components.get(0).dispose();
            virusStructure.components.get(2).dispose();
            baseLocations.clear();
            baseLocations.add(virusStructure.getCenter());
            periods.get(1).play();
        });

        // Giai đoạn 3:
        periods.get(1).setOnFinished( e -> 
        {
            count = 0;
            angle = -30;
            startLocation = new Location(0, -radius);
            endLocation = new Location((int) (radius * Math.cos(Math.toRadians(angle))), (int) (radius * Math.sin(Math.toRadians(angle))));
            periods.get(2).play();
        });
        
        // Giai đoạn 4:
        periods.get(2).setOnFinished(e -> 
        {
            count = 0;
            angle = -30;
            startLocation = new Location(0, -radius);
            endLocation = new Location((int) (radius * Math.cos(Math.toRadians(angle))), (int) (radius * Math.sin(Math.toRadians(angle))));
            periods.get(3).play();
        });

        // Giai đoạn 5: 
        periods.get(3).setOnFinished(e -> 
        {
            count = 0;
            angle = 0;
            startLocation = new Location(0, -radius);
            endLocation = new Location((int) (radius * Math.cos(Math.toRadians(angle))), (int) (radius * Math.sin(Math.toRadians(angle))));
            periods.get(4).play();
        });
        periods.get(4).setOnFinished(e -> 
        {
            count = 0;
            angle = 0;
            startLocation = new Location(0, -radius);
            endLocation = new Location((int) (radius * Math.cos(Math.toRadians(angle))), (int) (radius * Math.sin(Math.toRadians(angle))));
            periods.get(5).play();
        });
        periods.get(5).setOnFinished(e -> 
        {
            count = 0;
            angle = 0;
            startLocation = new Location(0, -radius);
            endLocation = new Location((int) (radius * Math.cos(Math.toRadians(angle))), (int) (radius * Math.sin(Math.toRadians(angle))));
            periods.get(6).play();
        });
        periods.get(6).setOnFinished(e -> 
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
            periods.get(7).play();
        });
    }
    public void dispose()
    {
        super.dispose();
        nucleoids.forEach(nucleoid -> nucleoid.dispose());
        enzymes.forEach(enzyme -> enzyme.dispose());
        hivs.forEach(hiv -> hiv.dispose());
    }
}

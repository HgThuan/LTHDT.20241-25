package  n25;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class Rhinovirus extends Virus{
    private final int TIME = 5000;
    public Rhinovirus(String name, Location center, int radius, int unitSize){
        this.name = name;
        this.radius = radius;
        this.unitSize = unitSize;

        List<Location> enzymeLocations = new ArrayList<>();
        enzymeLocations.add(center.clone());
        enzymeLocations.add(center.clone());
        enzymeLocations.get(0).move(new Vector_2D(-radius / 3, 0));
        enzymeLocations.get(1).move(new Vector_2D(radius / 3, 0));
        List<VirusComponent> components = List.of(
            new Nucleoid(center.clone(), radius / 2, unitSize, Color.GREEN), 
            new Capsit(center.clone(), radius, unitSize, Color.GOLD, Color.BLUE, ComponentStyle.CIRCLE_STYLE, SubComponentType.ANTIGEN),
            new Enzyme(enzymeLocations.get(0), radius / 3, unitSize, Color.BLACK),
            new Enzyme(enzymeLocations.get(1), radius / 3, unitSize, Color.BLACK)
        );
        VirusStructure virusStructure = new VirusStructure(components, center);
        this.virusStructure = virusStructure;
    }
    private int angle;
    private List<Rhinovirus> rhinoviruses = new ArrayList<>();
    private List<Vector_2D> speeds = new ArrayList<>();
    private List<Nucleoid> nucleoids = new ArrayList<>();
    private List<Enzyme> enzymes = new ArrayList<>();

    //Các biến dùng để vẽ thành phần virus
    List<Shape> shapes = new ArrayList<>();
    int circleCountForCircle;
    int count;
    Location startLocation, endLocation;
    Vector_2D drawVector;
    List<Location> baseLocations = new ArrayList<>();

    @Override
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
        
        //Giai đoạn 2: Virus tổng hợp nucleoid và enzyme
        Timeline synthesis = new Timeline(new KeyFrame(Duration.millis(TIME / 8), e -> {
            Location nucleusLocation = new Location(cellLocation.x + (int) ((3) * radius * Math.cos(Math.toRadians(angle))), cellLocation.y + (int) ((3) * radius * Math.sin(Math.toRadians(angle))));
            baseLocations.add(nucleusLocation);
            Nucleoid nucleus = new Nucleoid(nucleusLocation, radius / 2, unitSize, Color.GREEN);
            nucleus.draw(area);
            nucleoids.add(nucleus);

            Location enzymeLocation1 = nucleusLocation.clone();
            enzymeLocation1.move(new Vector_2D(-radius / 3, 0));
            Enzyme enzyme1 = new Enzyme(enzymeLocation1, radius / 3, unitSize, Color.BLACK);
            enzyme1.draw(area);
            enzymes.add(enzyme1);
            
            Location enzumeLocation2 = nucleusLocation.clone();
            enzumeLocation2.move(new Vector_2D(radius / 3, 0));
            Enzyme enzyme2 = new Enzyme(enzumeLocation2, radius / 3, unitSize, Color.BLACK);
            enzymes.add(enzyme2);
            enzyme2.draw(area);
            angle += 90;
        }));
        synthesis.setCycleCount(3); 

        // Giai đoạn 3: 
        // Virus hoàn thiện các thành phần khác
        // Tạo vỏ capsit
        circleCountForCircle = (int) (Math.PI * radius / unitSize);
        Timeline createCapsit = new Timeline(new KeyFrame(Duration.millis(timeSleep), e -> 
        {
            double angle = 2 * Math.PI * count / circleCountForCircle;
            drawVector = new Vector_2D((int) (radius * Math.cos(angle)), (int) (radius * Math.sin(angle)));

            for (Location baseLocation : baseLocations)
            {
                Location location = baseLocation.clone();
                location.move(drawVector);
                Circle circle = new Circle(location.x, location.y, unitSize, Color.GOLD);
                shapes.add(circle);
                area.getChildren().add(circle);
            }

            count++;
        }));
        createCapsit.setCycleCount(circleCountForCircle);

         // Giai đoạn 4:
        // Tạo các thành phần phụ của virus
        // Tạo antigen
        Timeline createAntigen = new Timeline(new KeyFrame(Duration.millis(timeSleep), e -> 
        {
            double angle = 2 * Math.PI * count / 15;
            drawVector = new Vector_2D((int) (radius * Math.cos(angle)), (int) (radius * Math.sin(angle)));

            for (Location baseLocation : baseLocations)
            {
                Location location = baseLocation.clone();
                location.move(drawVector);
                Circle circle = new Circle(location.x, location.y, 2 * unitSize, Color.BLUE);
                shapes.add(circle);
                area.getChildren().add(circle);
            }

            count++;
        }));
        createAntigen.setCycleCount(15);

        // Giai đoạn 5:
        // Virus thoát khỏi tế bào
        // Khởi tạo speeds
        for (int i = 0; i < 4; i++)
        {
            Vector_2D speedRHINO = new Vector_2D((int) (3 * radius * timeSleep / TIME * Math.cos(Math.toRadians(180 + i * 90))), (int) (3 * radius * timeSleep / TIME * Math.sin(Math.toRadians(180 + i * 90))));
            speeds.add(speedRHINO);
        }
        Timeline getOut = new Timeline(new KeyFrame(Duration.millis(timeSleep), e -> 
        {
            for (int i = 0; i < 4; i++)
            {
                rhinoviruses.get(i).virusStructure.relocate(speeds.get(i));
            }
        }));
        getOut.setCycleCount(TIME / timeSleep);

        // Thực thi các giai đoạn
        // Giai đoạn 1:
        getIn.play();

        // Giai đoạn 2:
        angle = 270;
        getIn.setOnFinished(e -> 
        {
            virusStructure.components.get(1).dispose();
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
        createAntigen.setOnFinished(e -> 
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
            rhinoviruses.clear();
            rhinoviruses.add(this);
            for (int i = 1; i < 4; i++)
            {
                Rhinovirus rhinovirus = new Rhinovirus("RHINO", baseLocations.get(i), radius, unitSize);
                rhinovirus.displayStructure(area);
                rhinoviruses.add(rhinovirus);
            }
            getOut.play();
        });
    }
}
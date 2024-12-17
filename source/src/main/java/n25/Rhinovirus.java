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

        List<VirusComponent> components = List.of(
            new MatrixProtein(center.clone(), radius + radius/2, unitSize, Color.RED, Color.BLUE, SubComponentType.ANTIGENORENZYME),
            new Nucleoid(center.clone(), radius / 2, unitSize, Color.GREEN),
            new Capsit(center.clone(), radius, unitSize, Color.BLUE, Color.ALICEBLUE, ComponentStyle.CIRCLE_STYLE, SubComponentType.ANTIGENORENZYME)
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
    int circleCountForHexagon;
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
        // Tính toán vị trí dựa trên góc hiện tại
        Location synthesisLocation = new Location(
        cellLocation.x + (int) (2 * radius * Math.cos(Math.toRadians(angle))),
        cellLocation.y + (int) (2 * radius * Math.sin(Math.toRadians(angle)))
        );
        // Tạo và vẽ enzyme
        Enzyme enzyme = new Enzyme(synthesisLocation.clone(), radius , unitSize, Color.BROWN, Color.AQUAMARINE);
        enzyme.draw(area);
        enzymes.add(enzyme);
        // Tạo và vẽ nucleoid
        Nucleoid nucleoid = new Nucleoid(synthesisLocation.clone(), radius / 2, unitSize, Color.GREEN);
        nucleoid.draw(area);
        nucleoids.add(nucleoid);
        // Lưu vị trí cơ sở
        baseLocations.add(synthesisLocation);
        // Tăng góc để chuẩn bị cho vòng lặp tiếp theo
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
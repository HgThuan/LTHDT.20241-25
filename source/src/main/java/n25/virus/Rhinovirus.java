package  n25.virus;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import n25.Cell;
import n25.Location;
import n25.Vector_2D;
import n25.VirusStructure;
import n25.subcomponent.SubComponentType;
import n25.viruscomponent.Capsit;
import n25.viruscomponent.ComponentStyle;
import n25.viruscomponent.Enzyme;
import n25.viruscomponent.Nucleoid;
import n25.viruscomponent.VirusComponent;

public class Rhinovirus extends Virus{
    public Rhinovirus(Location center, int radius, int unitSize)
    {
        super(center, radius, unitSize);
        this.isEnvelopedVirus = false;

        List<Location> enzymeLocations = new ArrayList<>();
        enzymeLocations.add(center.clone());
        enzymeLocations.add(center.clone());
        enzymeLocations.get(0).move(new Vector_2D(-radius / 3, 0));
        enzymeLocations.get(1).move(new Vector_2D(radius / 3, 0));
        List<VirusComponent> components = List.of(
            new Nucleoid(center.clone(), radius / 2, unitSize, Color.RED), 
            new Capsit(center.clone(), radius, unitSize, Color.GOLD, Color.BLUE, ComponentStyle.CIRCLE_STYLE, SubComponentType.ANTIGEN),
            new Enzyme(enzymeLocations.get(0), radius / 3, unitSize, Color.GREEN),
            new Enzyme(enzymeLocations.get(1), radius / 3, unitSize, Color.GREEN)
        );
        VirusStructure virusStructure = new VirusStructure(components, center);
        this.virusStructure = virusStructure;
    }

    @Override
    public void displayInfection(Pane area, int timeSleep){
        virusStructure.draw(area);
        Location cellLocation = new Location(virusStructure.getCenter().x + radius * 7, virusStructure.getCenter().y);
        cell = new Cell(cellLocation, radius * 3, 5, Color.LIGHTBLUE, Color.BLACK);
        cell.draw(area);
        // Thiết lập các giai đoạn 
        // Giai đoạn 1:
        // Virus xâm nhập vào tế bào
        Vector_2D speed = new Vector_2D(5 * radius * timeSleep / TIME, 0);
        periods.add(new Timeline(new KeyFrame(Duration.millis(timeSleep), e -> 
        {
            virusStructure.relocate(speed);
        })));
        periods.get(0).setCycleCount(TIME / timeSleep);
        
        //Giai đoạn 2: Virus tổng hợp nucleoid và enzyme
        periods.add(new Timeline(new KeyFrame(Duration.millis(TIME / 8), e -> 
        {
            Location nucleusLocation = new Location(cellLocation.x + (int) (2 * radius * Math.cos(Math.toRadians(angle))), cellLocation.y + (int) (2 * radius * Math.sin(Math.toRadians(angle))));
            baseLocations.add(nucleusLocation);
            Nucleoid nucleus = new Nucleoid(nucleusLocation, radius / 2, unitSize, Color.RED);
            nucleus.draw(area);
            nucleoids.add(nucleus);

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
        circleCountForCircle = (int) (Math.PI * radius / unitSize);
        periods.add(new Timeline(new KeyFrame(Duration.millis(timeSleep), e -> 
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
        })));
        periods.get(2).setCycleCount(circleCountForCircle);

         // Giai đoạn 4:
        // Tạo các thành phần phụ của virus
        // Tạo antigen
        periods.add(new Timeline(new KeyFrame(Duration.millis(timeSleep), e -> 
        {
            double angle = 2 * Math.PI * count / 15;
            drawVector = new Vector_2D((int) (radius * Math.cos(angle)), (int) (radius * Math.sin(angle)));

            for (Location baseLocation : baseLocations)
            {
                Location location = baseLocation.clone();
                location.move(drawVector);
                Circle circle = new Circle(location.x, location.y, 1.5 * unitSize, Color.BLUE);
                shapes.add(circle);
                area.getChildren().add(circle);
            }

            count++;
        })));
        periods.get(3).setCycleCount(15);

        // Giai đoạn 5:
        // Virus thoát khỏi tế bào
        // Khởi tạo speeds
        for (int i = 0; i < 4; i++)
        {
            Vector_2D speedRHINO = new Vector_2D((int) (3 * radius * timeSleep / TIME * Math.cos(Math.toRadians(180 + i * 90))), (int) (3 * radius * timeSleep / TIME * Math.sin(Math.toRadians(180 + i * 90))));
            speeds.add(speedRHINO);
        }
        periods.add(new Timeline(new KeyFrame(Duration.millis(timeSleep), e -> 
        {
            for (int i = 0; i < 4; i++)
            {
                viruses.get(i).virusStructure.relocate(speeds.get(i));
            }
        })));
        periods.get(4).setCycleCount(TIME / timeSleep);

        // Thực thi các giai đoạn
        // Giai đoạn 1:
        periods.get(0).play();
        status = "Virus is penetrating into the cell";

        // Giai đoạn 2:
        angle = 270;
        periods.get(0).setOnFinished(e -> 
        {
            virusStructure.components.get(1).dispose();
            baseLocations.clear();
            baseLocations.add(virusStructure.getCenter());
            periods.get(1).play();
            status = "Virus is synthesizing nucleoid and enzyme";
        });

        // Giai đoạn 3:
        periods.get(1).setOnFinished( e -> 
        {
            count = 0;
            angle = -30;
            startLocation = new Location(0, -radius);
            endLocation = new Location((int) (radius * Math.cos(Math.toRadians(angle))), (int) (radius * Math.sin(Math.toRadians(angle))));
            periods.get(2).play();
            status = "Virus is completing capsit";
        });

        // Giai đoạn 4:
        periods.get(2).setOnFinished(e -> 
        {
            count = 0;
            angle = -30;
            startLocation = new Location(0, -radius);
            endLocation = new Location((int) (radius * Math.cos(Math.toRadians(angle))), (int) (radius * Math.sin(Math.toRadians(angle))));
            periods.get(3).play();
            status = "Virus is creating VP1, VP2, VP3, VP4";
        });

        // Giai đoạn 5: 
        periods.get(3).setOnFinished(e -> 
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
            viruses.clear();
            viruses.add(this);
            for (int i = 1; i < 4; i++)
            {
                Rhinovirus rhinovirus = new Rhinovirus(baseLocations.get(i), radius, unitSize);
                rhinovirus.displayStructure(area);
                viruses.add(rhinovirus);
            }
            periods.get(4).play();
            status = "Virus is escaping from the cell";
        });

        periods.get(4).setOnFinished(e -> 
        {
            status = "Completed";
        });
    }
}

package n25.virus;

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
import n25.subcomponent.SubComponentType;
import n25.viruscomponent.Capsit;
import n25.viruscomponent.ComponentStyle;
import n25.viruscomponent.Nucleoid;
import n25.viruscomponent.VirusComponent;

public class HAV extends Virus {
    public HAV(Location center, int radius, int unitSize)
    {
        super(center, radius, unitSize);    
        this.isEnvelopedVirus = false;
        this.radius = radius;
        this.unitSize = unitSize;

        List<VirusComponent> components = List.of(
            new Nucleoid(center.clone(), radius / 2, unitSize, Color.RED),
            new Capsit(center.clone(), radius, unitSize, Color.GOLD, Color.BLUE, ComponentStyle.HEXAGON_STYLE, SubComponentType.ANTIGEN)
        );
        this.virusStructure.addComponents(components);
    }

    @Override
    public void displayInfection(Pane area, int timeSleep) {
        this.area = area;
        virusStructure.draw(area);
        Location cellLocation = new Location(virusStructure.getCenter().x + radius * 7, virusStructure.getCenter().y);
        cell = new Cell(cellLocation, radius * 3, 5, Color.LIGHTBLUE, Color.BLACK);
        cell.draw(area);
        // Thiết lập các giai đoạn 
        periods.clear();    
        // Giai đoạn 1:
        // Virus xâm nhập vào tế bào
        Vector_2D speed = new Vector_2D(5 * radius * timeSleep / TIME, 0);
        periods.add(new Timeline(new KeyFrame(Duration.millis(timeSleep), e -> 
        {
            virusStructure.relocate(speed);
        })));
        periods.get(0).setCycleCount(TIME / timeSleep);
        
        // Giai đoạn 2:
        // Virus tổng hợp nucleoid
        periods.add(new Timeline(new KeyFrame(Duration.millis(TIME / 8), e -> 
        {
            Location nucleusLocation = new Location(cellLocation.x + (int) (2 * radius * Math.cos(Math.toRadians(angle))), cellLocation.y + (int) (2 * radius * Math.sin(Math.toRadians(angle))));
            baseLocations.add(nucleusLocation);
            Nucleoid nucleus = new Nucleoid(nucleusLocation, radius / 2, unitSize, Color.RED);
            nucleus.draw(area);
            nucleoids.add(nucleus);
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
                Circle circle = new Circle(newLocation.x, newLocation.y, 1.5 * unitSize);
                circle.setFill(Color.BLUE);  
                shapes.add(circle);
                area.getChildren().add(circle);
            }
            count++;
        })));
        periods.get(3).setCycleCount(18);

        // Giai đoạn 5:
        // Virus thoát khỏi tế bào
        // Khởi tạo speeds
        for (int i = 0; i < 4; i++)
        {
            Vector_2D speedHAV = new Vector_2D((int) (3 * radius * timeSleep / TIME * Math.cos(Math.toRadians(180 + i * 90))), (int) (3 * radius * timeSleep / TIME * Math.sin(Math.toRadians(180 + i * 90))));
            speeds.add(speedHAV);
        }
        periods.add(new Timeline(new KeyFrame(Duration.millis(timeSleep), e -> 
        {
            for (int i = 0; i < 4; i++)
            {
                viruses.get(i).virusStructure.relocate(speeds.get(i));
            }
        })));
        periods.get(4).setCycleCount(TIME / timeSleep);

        //--------------------------------------------------------------------------------
        // Thực thi các giai đoạn
        // Giai đoạn 1:
        status = "Virus is penetrating into the cell";
        periods.get(0).play();

        // Giai đoạn 2:
        angle = 270;
        periods.get(0).setOnFinished(e -> 
        {
            status = "Virus is synthesizing nucleoid";
            virusStructure.components.get(1).dispose();
            baseLocations.clear();
            baseLocations.add(virusStructure.getCenter());
            periods.get(1).play();
        });

        // Giai đoạn 3:
        periods.get(1).setOnFinished( e -> 
        {
            status = "Viruses are creating capsit";
            count = 0;
            angle = -30;
            startLocation = new Location(0, -radius);
            endLocation = new Location((int) (radius * Math.cos(Math.toRadians(angle))), (int) (radius * Math.sin(Math.toRadians(angle))));
            periods.get(2).play();
        });
        
        // Giai đoạn 4:
        periods.get(2).setOnFinished(e -> 
        {
            status = "Viruses are creating VP1 and VP3";
            count = 0;
            angle = -30;
            startLocation = new Location(0, -radius);
            endLocation = new Location((int) (radius * Math.cos(Math.toRadians(angle))), (int) (radius * Math.sin(Math.toRadians(angle))));
            periods.get(3).play();
        });

        // Giai đoạn 5: 
        periods.get(3).setOnFinished(e -> 
        {
            status = "Virus is escaping from the cell";
            cell.dispose();
            nucleoids.forEach(nucleoid -> nucleoid.dispose());
            nucleoids.clear();
            shapes.forEach(shape -> area.getChildren().remove(shape));
            shapes.clear();
            viruses.clear();
            viruses.add(this);
            for (int i = 1; i < 4; i++)
            {
                HAV hav = new HAV(baseLocations.get(i), radius, unitSize);
                hav.displayStructure(area);
                viruses.add(hav);
            }
            periods.get(4).play();
        });

        periods.get(4).setOnFinished(e -> 
        {
            status = "Completed";
        });
    }
}

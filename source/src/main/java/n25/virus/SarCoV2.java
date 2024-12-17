package n25.virus;

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
import n25.subcomponent.Glycoprotein;
import n25.subcomponent.Spike;
import n25.subcomponent.SubComponentType;
import n25.viruscomponent.Envelope;
import n25.viruscomponent.Enzyme;
import n25.viruscomponent.Nucleoid;
import n25.viruscomponent.VirusComponent;

public class SarCoV2 extends Virus {
    public SarCoV2(Location center, int radius, int unitSize) {
        super(center, radius, unitSize);
        this.isEnvelopedVirus = true;
        
        List<Location> enzymeLocations = new ArrayList<>();
        enzymeLocations.add(center.clone());
        enzymeLocations.add(center.clone());
        enzymeLocations.get(0).move(new Vector_2D(-radius / 3, 0));
        enzymeLocations.get(1).move(new Vector_2D(radius / 3, 0));
        List<VirusComponent> components = List.of(
            new Nucleoid(center.clone(), radius / 2, unitSize, Color.RED),
            new Envelope(center.clone(), (int) (radius * 1.5), unitSize, Color.GREEN, Color.RED, SubComponentType.GLYCOPROTEINANDSPIKE),
            new Enzyme(enzymeLocations.get(0), radius / 3, unitSize, Color.GREEN),
            new Enzyme(enzymeLocations.get(1), radius / 3, unitSize, Color.GREEN)
        );
        this.virusStructure.addComponents(components);  
    }

    @Override
    public void displayInfection(Pane area, int timeSleep){
        virusStructure.draw(area);
        Location cellLocation = new Location(virusStructure.getCenter().x + radius * 8, virusStructure.getCenter().y);
        cell = new Cell(cellLocation, radius * 5, 5, Color.LIGHTBLUE, Color.BLACK);
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
                Circle circle = new Circle(newLocation.x, newLocation.y, unitSize);
                circle.setFill(Color.GREEN); // Màu sắc của điểm
                shapes.add(circle);
                area.getChildren().add(circle);
            }
        
            // Tăng bộ đếm để vẽ điểm tiếp theo
            count++;
        })));
        periods.get(2).setCycleCount(circleCountForEnvelope);

        // Giai đoạn 4:
        // Tạo các thành phần phụ của virus
        // Tạo glycoprotein và spike
        periods.add(new Timeline(new KeyFrame(Duration.millis(timeSleep), e -> {
            
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
        })));
        periods.get(3).setCycleCount(30);
        
        // Giai đoạn 5:
        // Virus thoát khỏi tế bào
        // Khởi tạo speeds
        for (int i = 0; i < 4; i++)
        {
            Vector_2D speedSarCoV2 = new Vector_2D((int) (3 * radius * timeSleep / TIME * Math.cos(Math.toRadians(180 + i * 90))), (int) (3 * radius * timeSleep / TIME * Math.sin(Math.toRadians(180 + i * 90))));
            speeds.add(speedSarCoV2);
        }
        periods.add(new Timeline(new KeyFrame(Duration.millis(timeSleep), e -> {
            
            for (int i = 0; i < 4; i++)
            {
                viruses.get(i).virusStructure.relocate(speeds.get(i));
            }
        })));
        periods.get(4).setCycleCount(TIME / timeSleep);

        //--------------------------------------------------------------------------------
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
            status = "Virus is synthesizing nucleoid";
        });

        // Giai đoạn 3: 
        periods.get(1).setOnFinished(e ->{ 
            count = 0;
            angle = 0;
            startLocation = new Location(0, -radius);
            endLocation = new Location((int) (radius * Math.cos(Math.toRadians(angle))), (int) (radius * Math.sin(Math.toRadians(angle))));
            periods.get(2).play();
            status = "Viruses are creating lipit envelope";
        });

        // Giai đoạn 4:
        periods.get(2).setOnFinished(e ->{ 
            count = 0;
            angle = 0;
            startLocation = new Location(0, -radius);
            endLocation = new Location((int) (radius * Math.cos(Math.toRadians(angle))), (int) (radius * Math.sin(Math.toRadians(angle))));
            periods.get(3).play();
            status = "Viruses are creating glycoprotein and spike";
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
                SarCoV2 sarCoV2 = new SarCoV2(baseLocations.get(i), radius, unitSize);
                sarCoV2.displayStructure(area);
                viruses.add(sarCoV2);
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

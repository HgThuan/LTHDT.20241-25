package n25;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class SarCoV2 extends Virus {
    public SarCoV2(String name, Location center, int radius, int unitSize) {
        this.name = name;
        this.radius = radius;
        this.unitSize = unitSize;

        List<VirusComponent> components = List.of(
            new Nucleoid(center.clone(), radius / 2, unitSize, Color.GREEN),
            new Capsit(center.clone(), radius, unitSize, Color.GOLD, Color.BLUE, ComponentStyle.HEXAGON_STYLE, SubComponentType.ANTIGEN),
            new MatrixProtein(center, (int) (radius * 1.35), unitSize, Color.RED, Color.BLUE, SubComponentType.NONE),
            new Envelope(center, (int) (radius * 1.5), unitSize, Color.YELLOW, Color.RED, SubComponentType.GLYCOPROTEINANDSPIKE)
        );
        VirusStructure virusStructure = new VirusStructure(components, center);
        this.virusStructure = virusStructure;
    }
    
    public void displayInfection(Pane area, int timeSleep){
        
    }
}

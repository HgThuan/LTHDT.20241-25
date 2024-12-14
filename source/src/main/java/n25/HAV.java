package n25;

import javafx.scene.layout.Pane;

public class HAV extends Virus {
    
    public HAV(String name, Location center)
    {
        this.name = name;
        this.center = center;
    }

    @Override
    public void displayInfection(Pane area, int componentStyle, int subComponentType, int numSteps, int timeSleep) {
        virusStructure.draw(area, componentStyle, subComponentType, numSteps);
        
    }
}

package n25;

import javafx.scene.layout.Pane;

public abstract class Virus {
    
    protected String name;
    protected VirusStructure virusStructure;

    public void displayStructure(Pane area, int componentStyle, int subComponentType, int numSteps){
        virusStructure.draw(area, componentStyle, subComponentType, numSteps);
    }

    public abstract void displayInfection(Pane area, int componentStyle, int subComponentType, int numSteps);
}

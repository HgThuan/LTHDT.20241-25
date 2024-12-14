package n25;

import javafx.scene.layout.Pane;

public abstract class Virus {
    
    protected String name;
    protected VirusStructure virusStructure;
    protected Location center;
    protected int radius;
    protected int unitSize;
    
    public void displayStructure(Pane area){
        virusStructure.draw(area);
    }

    public abstract void displayInfection(Pane area, int timeSleep);
}

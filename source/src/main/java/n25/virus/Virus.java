package n25.virus;

import javafx.scene.layout.Pane;
import n25.VirusStructure;

public abstract class Virus {
    protected boolean isEnvelopedVirus;
    protected String name;
    protected VirusStructure virusStructure;
    protected int radius;
    protected int unitSize;
    
    public String type()
    {
        if (isEnvelopedVirus)
            return "Enveloped Virus";
        return "Non-Enveloped Virus";
    }
    public void displayStructure(Pane area){
        virusStructure.draw(area);
    }

    public abstract void displayInfection(Pane area, int timeSleep);
}

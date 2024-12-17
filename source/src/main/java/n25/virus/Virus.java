package n25.virus;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import n25.VirusStructure;
import n25.viruscomponent.VirusComponent;

public abstract class Virus {
    protected boolean isEnvelopedVirus;
    protected String name;
    protected VirusStructure virusStructure;
    protected int radius;
    protected int unitSize;
    
    public String status = "";

    protected List<Timeline> periods = new ArrayList<>();
    protected Timeline pausedTimeline;
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

    public void pause()
    {
        for (Timeline period : periods)
        {
            if (period.getStatus() == Timeline.Status.RUNNING)
            {
                pausedTimeline = period;
                period.pause();
                break;
            }
        }
    }

    public void resume()
    {
        if (pausedTimeline != null)
        {
            pausedTimeline.play();
        }
    }

    public void dispose()
    {
        for (Timeline period : periods)
        {
            period.stop();
        }
        for (VirusComponent component : virusStructure.components)
        {
            component.dispose();
        }
    }
}

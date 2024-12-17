package n25.virus;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import n25.Cell;
import n25.Location;
import n25.Vector_2D;
import n25.VirusStructure;
import n25.viruscomponent.Enzyme;
import n25.viruscomponent.Nucleoid;

public abstract class Virus {
    // Các thuộc tính của virus
    protected boolean isEnvelopedVirus;
    protected VirusStructure virusStructure;
    protected int radius;
    protected int unitSize;
    
    // Trạng thái của virus
    public String status = "";

    // Các timeline quản lý các giai đoạn của virus
    protected List<Timeline> periods = new ArrayList<>();
    protected Timeline pausedTimeline;

    // Thời gian mỗi giai đoạn
    protected final int TIME = 5000;

    // Các biến dùng để vẽ quá trình lây nhiễm của virus
    protected int angle;
    protected List<Virus> viruses = new ArrayList<>();
    protected List<Vector_2D> speeds = new ArrayList<>();
    protected List<Nucleoid> nucleoids = new ArrayList<>();
    protected List<Enzyme> enzymes = new ArrayList<>();

    // Các biến dùng để vẽ các thành phần của virus
    protected List<Shape> shapes = new ArrayList<>();
    protected int circleCountForCircle;
    protected int circleCountForHexagon;
    protected int count;
    protected Location startLocation, endLocation;
    protected Vector_2D drawVector;
    protected List<Location> baseLocations = new ArrayList<>();

    // Biến dùng để vẽ tế bào
    protected Cell cell;

    public Virus(Location center, int radius, int unitSize)
    {
        this.radius = radius;
        this.unitSize = unitSize;
        this.virusStructure = new VirusStructure(new ArrayList<>(), center);
    }
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
        nucleoids.forEach(nucleoid -> nucleoid.dispose());
        enzymes.forEach(enzyme -> enzyme.dispose());
        for (Virus virus : viruses)
        {
            if (virus == this)
                continue;
            virus.dispose();
        }
        periods.forEach(period -> period.stop());
        virusStructure.components.forEach(component -> component.dispose());
        if (cell != null)
        {
            cell.dispose();
        }
    }
}

package n25;

import javafx.scene.shape.Shape;

public class VirusComponent {
    Location center;
    int radius;
    SubComponent subComponent[];
    Shape[] shapes;

    public void relocate(Location location)
    {
        center.setLocation(location);
        for (int i = 0; i < subComponent.length; i++)
        {
            int[] directions = Location.toDirection(center, location);
            subComponent[i].relocate(directions[0], directions[1]);
        }    
    }
}
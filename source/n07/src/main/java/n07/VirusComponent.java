package n07;

public class VirusComponent {
    Location center;
    int radius;
    boolean drawController = true;

    public void relocate(Location location)
    {
        center.setLocation(location);
        if (drawController)
        {
            // draw controller
        }
    }
}
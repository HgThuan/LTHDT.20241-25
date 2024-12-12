package n25;

import java.util.List;

import javafx.scene.paint.Color;

public class SubComponentType {
    public static final int NONE = 0;
    public static final int ANTIGEN = 1;
    public static final int GLYCOPROTEIN = 2;
    public static final int ANTIGENANDGLYCOPROTEIN = 3;

    public static void createSubComponent(List<Location> drawLocations, Location center, int subComponentStyle, List<SubComponent> subComponents, int unitSize, Color color) {
        switch (subComponentStyle) {
            case ANTIGEN:
                for (int i = 0; i < drawLocations.size(); i += 2)
                {
                    Location location = drawLocations.get(i);
                    Antigen antigen = new Antigen(location, unitSize, color);
                    subComponents.add(antigen);
                }
                break;
            case GLYCOPROTEIN:
                for (int i = 1; i < drawLocations.size(); i += 2)
                {
                    Location location = drawLocations.get(i);
                    Vector_2D subtract = Location.subtract(center, location);
                    int direction = (int) Math.toDegrees(Math.atan2(subtract.y, subtract.x));
                    Glycoprotein glycoprotein = new Glycoprotein(location, direction, unitSize, color);
                    subComponents.add(glycoprotein);
                }
                break;
            case ANTIGENANDGLYCOPROTEIN:
                for (int i = 0; i < drawLocations.size(); i++)
                {
                    Location location = drawLocations.get(i);
                    if (i % 2 == 0)
                    {
                        Antigen antigen = new Antigen(location, unitSize, color);
                        subComponents.add(antigen);
                    }
                    else
                    {
                        Vector_2D subtract = Location.subtract(center, location);
                        int direction = (int) Math.toDegrees(Math.atan2(subtract.y, subtract.x));
                        Glycoprotein glycoprotein = new Glycoprotein(location, direction, unitSize, color);
                        subComponents.add(glycoprotein);
                    }
                }
                break;
            default:
                break;
        }
    }
}

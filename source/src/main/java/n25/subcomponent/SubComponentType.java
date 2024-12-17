package n25.subcomponent;

import java.util.List;

import javafx.scene.paint.Color;
import n25.Location;

public class SubComponentType {
    public static final int NONE = 0;
    public static final int ANTIGEN = 1;
    public static final int GLYCOPROTEIN = 2;
    public static final int SPIKE = 4;
    public static final int ANTIGENANDGLYCOPROTEIN = 3;
    public static final int ANTIGENANDSPIKE = 5;
    public static final int GLYCOPROTEINANDSPIKE = 6;
    public static final int ANTIGENANDGLYCOPROTEINANDSPIKE = 7;

    public static void createSubComponent(List<Location> drawLocations, Location center, int subComponentStyle, List<SubComponent> subComponents, int unitSize, Color color) {
        int antigen = subComponentStyle % 2;
        int glycoprotein = (subComponentStyle / 2) % 2;
        int spike = (subComponentStyle / 4) % 2;
        int numberOfSubComponents = antigen + glycoprotein + spike;
        if (numberOfSubComponents == 0)
            return;
        for (int i = 0; i < drawLocations.size();)
        {
            if (antigen == 1)
            {
                subComponents.add(new Antigen(drawLocations.get(i), unitSize, color));
                i++;
            }
            if (glycoprotein == 1)
            {
                subComponents.add(new Glycoprotein(drawLocations.get(i), i * 360 / drawLocations.size(), unitSize, color));
                i++;
            }
            if (spike == 1)
            {
                subComponents.add(new Spike(drawLocations.get(i), i * 360 / drawLocations.size(), unitSize, color));
                i++;
            }
            if (numberOfSubComponents == 1)
                i++;
        }
    }
}

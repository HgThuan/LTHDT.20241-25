package n25;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;

public class VirusStructure {
    private List<VirusComponent> components = new ArrayList<>();

    public VirusStructure() {
    }

    public VirusStructure(List<VirusComponent> components) {
        this.components = components;
    }

    public void relocate(Location location) {
        for (VirusComponent component : components) {
            component.relocate(location);
        }
    }

    public void relocate(Vector_2D vector) {
        for (VirusComponent component : components) {
            component.relocate(vector);
        }
    }

    public void addComponent(VirusComponent component) {
        components.add(component);
    }

    public void addComponents(List<VirusComponent> components) {
        this.components.addAll(components);
    }

    public void removeComponent(VirusComponent component) {
        components.remove(component);
    }

    public void removeComponents(List<VirusComponent> components) {
        this.components.removeAll(components);
    }

    public void draw(Pane area, int componentStyle, int subComponentType, int numSteps) {
        for (VirusComponent component : components) {
            if (component instanceof Nucleoid)
            {
                component.draw(area, subComponentType, numSteps);
            }
            else
            {
                component.draw(area, componentStyle, subComponentType);
            }
        }
    }
}

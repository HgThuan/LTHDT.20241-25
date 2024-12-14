package n25;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;

public class VirusStructure {
    public List<VirusComponent> components = new ArrayList<>();

    public VirusStructure() {
    }

    public VirusStructure(List<VirusComponent> components) {
        this.components = components;
    }

    public void relocate(Location location) {
        for (VirusComponent component : components) {
            component.relocate(location);
            component.draw();
        }
    }

    public void relocate(Vector_2D vector) {
        for (VirusComponent component : components) {
            component.relocate(vector);
            component.draw();
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

    public void draw() {
        try
        {
            for (VirusComponent component : components) {
                component.draw();
            }
        }
        finally{}
    }

    public void draw(Pane area) {
        for (VirusComponent component : components) {
            component.draw(area);
        }
    }
}

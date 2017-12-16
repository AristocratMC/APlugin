package aplugin.classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aristocrat on 12/15/2017.
 */
public class ActionBarMessage {
    private List<ActionBarMessageComponent> componentList;

    public ActionBarMessage() {
        componentList = new ArrayList<>();
    }

    public List<ActionBarMessageComponent> getComponentList() {
        return componentList;
    }

    public String toString() {
        StringBuilder barResult = new StringBuilder();

        componentList.forEach(component -> barResult.append(component.toString()).append(' '));

        return barResult.deleteCharAt(barResult.length() - 1).toString();
    }
}

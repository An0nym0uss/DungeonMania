
package dungeonmania.entities.player;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private List<String> ingredients = new ArrayList<String>();
    private String buildableEntityId;

    public boolean isCraftable(Inventory inventory) {

        return false;
    }
}
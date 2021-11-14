package dungeonmania;

import dungeonmania.constants.Layer;
import dungeonmania.entities.Entity;
import dungeonmania.entities.collectable.Treasure;
import dungeonmania.entities.collectable.rarecollectable.TheOneRing;
import dungeonmania.entities.enemy.Mercenary;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.statics.Wall;
import dungeonmania.modes.Mode;
import dungeonmania.modes.Standard;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
public class AssassinTest {
    @Test
    public void testAssassinBribed() {
        Mercenary mercenary = new Mercenary(new Position(2, 1), 1, 1, 1);
        Player player = new Player(new Position(0, 0), new Standard());
        Treasure treasure = new Treasure(new Position(1, 0));
        TheOneRing oneRing = new TheOneRing(new Position(1, 1));

        /**
         * [P] [0] []
         * [] [R] [M]
         * [] [] []
         *
         */
        Entity[][][] map = new Entity[3][3][Layer.LAYER_SIZE];
        map[player.getPosition().getX()][player.getPosition().getY()][player.getPosition().getLayer()] = player;
        map[mercenary.getPosition().getX()][mercenary.getPosition().getY()][mercenary.getPosition().getLayer()] = mercenary;
        map[treasure.getPosition().getX()][treasure.getPosition().getY()][treasure.getPosition().getLayer()] = treasure;
        map[oneRing.getPosition().getX()][oneRing.getPosition().getY()][oneRing.getPosition().getLayer()] = oneRing;

        Grid grid = new Grid(3, 3, map, player);
        mercenary.update(grid);

        assertEquals(2, mercenary.getPosition().getX());
        assertEquals(0, mercenary.getPosition().getY());
    }
}

package dungeonmania.entities.enemy;

import dungeonmania.Grid;
import dungeonmania.entities.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entities.enemy.Mercenary;

import java.util.List;
import java.util.*;

public class Assassin extends Mercenary {
    private Mercenary mercenary;

    public Assassin(Position position, int speed, int health, int damage) {
        super("assassin", position, false, speed, health, damage);
    }

    @Override
    public void update(Grid grid) {
        mercenary.update(grid);
    }

    public List<Position> breadthFirstSearch(Grid grid) {
        return mercenary.breadthFirstSearch(grid);
    }


    @Override
    public void move(Grid grid, Direction d) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean movingConstraints(Entity e) {
        return mercenary.movingConstraints(e);
    }
}

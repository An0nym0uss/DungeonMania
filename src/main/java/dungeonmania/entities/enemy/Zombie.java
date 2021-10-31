package dungeonmania.entities.enemy;

import dungeonmania.Grid;
import dungeonmania.entities.Entity;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.statics.Boulder;
import dungeonmania.entities.statics.Door;
import dungeonmania.entities.statics.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.Collections;
import java.util.List;

public class Zombie extends Enemy {

    public Zombie(Position position, int speed, int health, int damage) {
        super("zombie_toast", position, false, speed, health, damage);
    }

    @Override
    public void update(Grid grid) {
        // TODO: Incorporate speed?
        // TODO: Random Movement -> Shuffling 'adjacentSquares'
        // TODO: Filter out the "diagonal" positions
        // Update the movement (with every tick)
        // if space is not constriant, then move entity to that space
        // Check moving constraints (eg, can it go through walls/portals, etc)
        //
        // Gets the adjacent squares of the zombie
        // NW N NE E SE S SW W
        List<Position> adjacentSquares = this.getPosition().getAdjacentCardinalPositions();
        // free = not collectable?

        // Randomly shuffles not sure if pseudo random or purely random.
        Collections.shuffle(adjacentSquares);

        // for(int i = 0; i < speed; i++) // requirement cannot backtrack
        for (Position position : adjacentSquares) {
            if (this.canMoveToPosition(grid, position)) {
                this.setPosition(position);
            }
        }
    }

    @Override
    public int damageDealt() {
        return 0;
    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public void move(Grid grid, Direction d) {
        
    }
    /**
     * Determines if the specified entity constrains the movement of this class
     */
    @Override
    public boolean movingConstraints(Entity e) {
        if (!super.canMoveInto(e))                   { return false; }
        else if (e instanceof Wall) {
            return true;
        }
        if (e instanceof Door && ((Door) e).getIsOpen() == false) {
            return true;
        }
        if (e instanceof Boulder) {
            return true;
        }
        if (e instanceof Enemy) {
            return true;
        }
        if (e instanceof Player) {
            return true;
        }
        return false;
    }
}

package dungeonmania.entities.enemy;

import dungeonmania.Grid;
import dungeonmania.entities.Damage;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Health;
import dungeonmania.entities.Moving;
import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.statics.Boulder;
import dungeonmania.entities.statics.Door;
import dungeonmania.entities.statics.Portal;
import dungeonmania.entities.statics.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Zombie extends Enemy {

    public Zombie(int speed, Health health, Moving moving, Damage damage) {
        super(speed, health, moving, damage);
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
            // Check if position is free
            //
            List<Entity> positionEntities = grid.getEntities(position.getX(), position.getY());
            // zombie -
            // - free spaces = portal, collectables,
            // - non-free spaces = wall, doors, boulders, other moving entities
            // positionEntities = [Coin, Zombie, Exit]
            // nonFreeSpaces = [Wall, Door, Boulder, EnemyEntity, Player]
            //List<Entity> nonFreeSpaces = Wall, Door, Boulder, Enemy, Player;

            // isEntityConstraint() --> false; true
            boolean canMoveToPosition = true;
            for (Entity positionEntity : positionEntities) {
                if (this.movingConstraints(positionEntity)) {
                    // Entity cannot move to this square -> check the next square
                    canMoveToPosition = false;
                    break;
                }
            }

            // if square is "free" perform the actual movement
            if (canMoveToPosition) {
                this.setPosition(position.getX(), position.getY());
                break;
            }
        }
    }

    @Override
    public int damageDealt(Entity e) {
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
        if (e instanceof Wall) {
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

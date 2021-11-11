package dungeonmania.entities.enemy;

import java.util.List;
import java.util.Random;

import dungeonmania.Grid;
import dungeonmania.constants.Layer;
import dungeonmania.entities.Battle;
import dungeonmania.entities.Entity;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.statics.Boulder;
import dungeonmania.entities.statics.Door;
import dungeonmania.entities.statics.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/**
 * This enemy entity moves by picking a random valid direction. (N, S, E, or W)
 */
public abstract class RandomMovingEnemy extends Enemy {
    
    protected static Random random = new Random(0);

    public RandomMovingEnemy(String type, Position position, Boolean isInteractable, int speed, int health, int damage) {
        super(type, position, isInteractable, speed, health, damage);
    }

    @Override
    public void move(Grid grid, Direction d) {
        List<Position> adjacentSquares = this.getPosition().getAdjacentCardinalPositions();
        Position newPosition = null;
        Entity doBattle = null;

        while (newPosition == null && adjacentSquares.size() > 0) { // pick random directions until none are left
            int nextMove = random.nextInt(adjacentSquares.size());
            newPosition = adjacentSquares.remove(nextMove);

            int x = newPosition.getX();
            int y = newPosition.getY();
            if (x >= 0 && x < grid.getWidth() &&
                y >= 0 && y < grid.getHeight()
            ) { // check grid boundary
                List<Entity> positionEntities = grid.getEntities(newPosition.getX(), newPosition.getY());
                for (Entity positionEntity : positionEntities) {
                    if (positionEntity instanceof Player) { // do a battle with player
                        doBattle = positionEntity;
                    }
                    else if (movingConstraints(positionEntity)) { // can't move here
                        newPosition = null;
                    }
                }
            }
            else {
                newPosition = null;
            }
        }

        if (newPosition != null) {
            grid.dettach(this);
            setPosition(new Position(newPosition.getX(), newPosition.getY(), Layer.ENEMY));
            grid.attach(this);
        }

        if (doBattle != null) {
            commenceBattle(grid);
        }
    }

    @Override
    public boolean movingConstraints(Entity e) {
        if (e instanceof Wall) {
            return true;
        }
        else if (e instanceof Door && ((Door) e).getIsOpen() == false) {
            return true;
        }
        else if (e instanceof Boulder) {
            return true;
        }
        else if (e instanceof Enemy) {
            return true;
        }
        return false;
    }
}

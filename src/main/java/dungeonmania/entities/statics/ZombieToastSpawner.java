package dungeonmania.entities.statics;

import java.util.List;

import dungeonmania.Grid;
import dungeonmania.constants.Layer;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Spawner;
import dungeonmania.entities.enemy.Zombie;
import dungeonmania.modes.Mode;
import dungeonmania.util.Position;

/**
 * Spawns zombie toasts every 20 ticks (15 in hard mode) in an open square cardinally adjacent to the spawner. 
 * The character can destroy a zombie spawner if they have a weapon and are cardinally adjacent to the spawner.
 * @author Lachlan Kerr (z5118613)
 */
public class ZombieToastSpawner extends StaticEntity implements Spawner  {

    private int spawnRate;
    private int spawnCounter = 0;

    /**
     * Creates a new zombie toast spawner at the given position.
     * @param position The position of the zombie toast spawner.
     * @param mode The current game mode.
     */
    public ZombieToastSpawner(Position position, Mode mode) {
        super("zombie_toast_spawner", position, true);
        spawnRate = mode.getZombieSpawnRate();
    }

    @Override
    public void update(Grid grid) {
        spawnCounter++;
        if (spawnCounter == spawnRate) {
            spawnCounter = 0;

            Position zombieSpawnerPosition = getPosition();

            List<Position> validPositions = zombieSpawnerPosition.getAdjacentCardinalPositions();

            Zombie zombie = new Zombie(zombieSpawnerPosition, 1, 1, 1); //use a temp position for now

            for (Position position : validPositions) {
                List<Entity> entitiesAtAdjacentPosition = grid.getEntities(position.getX(), position.getY());
                position = new Position(position.getX(), position.getY(), Layer.ENEMY);
                zombie.setPosition(position);
                boolean canMove = true;

                //check if there's anything in position that won't allow our zombie
                for (Entity entity : entitiesAtAdjacentPosition) {
                    if (zombie.movingConstraints(entity)) {
                        canMove = false;
                    }
                }

                if (canMove) {
                    spawn(zombie, grid);
                    return;
                }
            }
        }
    }

    @Override
    public void spawn(Entity entity, Grid grid) {
        grid.attach(entity);
    }
}

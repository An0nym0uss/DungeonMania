package dungeonmania.entities.statics;

import dungeonmania.Grid;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

/**
 * A solid wall, only spiders can move onto them.
 */
public class Wall extends StaticEntity {
    
    /**
     * Constructor for wall.
     * 
     * @param position
     */
    public Wall(Position position) {
        super("wall", position, false);
    }

    @Override
    public void collidesWith(Entity other, Grid grid) {
        /* Can uncomment when Spider extends Entity
        if (other instanceof Spider) {
            Spider spider = (Spider)other;
            Position wallPosition = this.getPosition();

            List<Entity> entitiesAtDoorPosition = grid.getEntities(wallPosition.getX(), wallPosition.getY());

            boolean canMove = true;

            //check if theres anything in wall position that won't allow our spider, i.e. another spider
            for (Entity entity : entitiesAtDoorPosition) {
                if (!spider.canMoveInto(entity)) {
                    canMove = false;
                }
            }

            if (canMove) {
                spider.setPosition(wallPosition); //spider moves to wall position
            }
        }
        */
    }

    @Override
    public boolean canMoveInto(Entity other) {
        /* Can uncomment when Spider extends Entity
        if (other instanceof Spider) { //required
            return true; 
        } 
        */  

        return false;
    }

}

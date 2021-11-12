package dungeonmania.entities.statics;

import java.util.List;

import dungeonmania.Grid;
import dungeonmania.entities.Entity;
import dungeonmania.entities.collectable.Bomb;
import dungeonmania.entities.enemy.Enemy;
/*
import dungeonmania.entities.collectable.Armour;
import dungeonmania.entities.collectable.Arrow;
import dungeonmania.entities.collectable.Bomb;
import dungeonmania.entities.collectable.HealthPotion;
import dungeonmania.entities.collectable.InvincibilityPotion;
import dungeonmania.entities.collectable.InvisibilityPotion;
import dungeonmania.entities.collectable.Key;
import dungeonmania.entities.collectable.Sword;
import dungeonmania.entities.collectable.Treasure;
import dungeonmania.entities.collectable.Wood;
import dungeonmania.entities.enemy.Mercenary;
import dungeonmania.entities.enemy.Spider;
import dungeonmania.entities.enemy.Zombie;
*/
import dungeonmania.entities.player.Player;
import dungeonmania.util.Position;

/**
 * A boulder that can be pushed around by the player, activates any floor switches it is placed on.
 */
public class Boulder extends StaticEntity {

    /**
     * Constructor for Boulder.
     * 
     * @param type
     * @param position
     */
    public Boulder(Position position) {
        super("boulder", position, false);
    }

    @Override
    public void collidesWith(Entity other, Grid grid) {
        // if (other instanceof Player) {
        //     Position playerPosition = other.getPosition();
        //     Position boulderPosition = this.getPosition();
        //     Position newPosition = reflectPosition(boulderPosition, playerPosition);

        //     List<Entity> entitiesAtNewPosition = grid.getEntities(newPosition.getX(), newPosition.getY());

        //     boolean canMove = true;

        //     //check if theres anything in new position that won't allow our boulder
        //     for (Entity entity : entitiesAtNewPosition) {
        //         if (!this.canMoveInto(entity)) {
        //             canMove = false;
        //         }
        //     }

        //     if (canMove) {
        //         this.setPosition(newPosition); //boulder moves to new position
        //         other.setPosition(boulderPosition); //player moves to boulders old position
        //     }
        // }
        //assume nothing else can move a boulder (since spiders are mean't to turn around, and the other moving entities are based off of it)
    }

    /**
     * Gets the reflection of a given position around the given origin. 
     * E.g. origin = {0,0}, other = {1,1}, returns {-1,-1}
     * E.g. origin = {1,1}, other = {1,0}, returns {1,2}
     * @param origin The centre of our reflection.
     * @param toReflect The position we wish to reflect.
     * @return The reflection of our given position.
     */
    // private Position reflectPosition(Position origin, Position toReflect) {
    //     int xOffset = origin.getX() - toReflect.getX();
    //     int yOffset = origin.getY() - toReflect.getY();
    //     return new Position(origin.getX() + xOffset, origin.getY() + yOffset);
    // }
    
    @Override
    public boolean canMoveInto(Entity other) {
        if (other instanceof Wall)                      { return false; }
        else if (other instanceof ZombieToastSpawner)   { return false; }
        else if (other instanceof Boulder)              { return false; }
        else if (other instanceof Bomb && 
                ((Bomb)other).hasPlaced())              { return false; }
        if (other instanceof Enemy)                     { return false; }
        else if (other instanceof Door)                 { 
            if (((Door)other).getIsOpen())              { return true; }    //assumption
            else                                        { return false; } } //required

        return true;
    }

    @Override
    public Boulder clone() {
        return new Boulder(this.getPosition());
    }
}

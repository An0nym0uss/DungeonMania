package dungeonmania.entities.player;

import dungeonmania.Grid;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;
import dungeonmania.entities.statics.*;
import dungeonmania.entities.enemy.*;
import dungeonmania.entities.collectable.*;

public class Player extends Entity implements GameToJSON {
    private int damage;
    private int maxHealth;
    private int currentHealth;
    private Inventory inventory;

    
    public Player(int attack, int health) {
        this.damage = attack;
        this.maxHealth = health;
        this.currentHealth = health;
        this.inventory = new Inventory();
    }


    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public int getCurrentHealth() {
        return this.currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    @Override
    public void collidesWith(Entity other, Grid grid) {
        if (canMoveInto(other)) {
            if (other instanceof Key) {
                // can only pick up one key
            } else if (other instanceof CollectableEntity) {
                inventory.addItem((CollectableEntity)other);
                this.setPosition(other.getPosition());
            } else if (other instanceof Enemy) {
                // enter battle
            } else if (other instanceof Door) {
                inventory.removeNonSpecificItem("key");
                this.setPosition(other.getPosition());
            } else {
                this.setPosition(other.getPosition());
            }
        }
    }

    @Override
    public boolean canMoveInto(Entity other) {
        
        if (other instanceof Wall)                      {return false;} 
        else if (other instanceof Exit)                 {return true;}    
        else if (other instanceof Boulder)              {return false;} 
        else if (other instanceof FloorSwitch)          {return true;}  
        else if (other instanceof Door) {
            if (inventory.checkItem("key") > 0)         {return true;}
            else                                        {return false;}
        }                            
        else if (other instanceof Portal)               {return true;}    
        else if (other instanceof ZombieToastSpawner)   {   
            if (inventory.checkItem("sword") > 0 || 
                inventory.checkItem("bow") > 0)         {return true;}
            else                                        {return false;}
        }
        // else if (other instanceof Spider)               {return true;} 
        // else if (other instanceof Zombie)               {return true;} 
        // else if (other instanceof Mercenary)            {return true;} 
        else if (other instanceof Treasure)             {return true;}  
        else if (other instanceof Key)                  {return true;}  
        else if (other instanceof HealthPotion)         {return true;}  
        else if (other instanceof InvincibilityPotion)  {return true;}  
        else if (other instanceof InvisibilityPotion)   {return true;}  
        else if (other instanceof Wood)                 {return true;}  
        else if (other instanceof Arrow)                {return true;}  
        // else if (other instanceof Bomb)                 { 
        //     if (((Bomb)other).getIsPlaced())            {return false;} 
        //     else                                        {return true;}
        // }
        else if (other instanceof Sword)                {return true;}  
        else if (other instanceof Armour)               {return true;}
        else                                            {return false;}
    }
};

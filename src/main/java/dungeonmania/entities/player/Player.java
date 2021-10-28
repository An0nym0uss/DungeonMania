package dungeonmania.entities.player;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Grid;
import dungeonmania.entities.Damage;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Health;
import dungeonmania.entities.Moving;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entities.statics.*;
import dungeonmania.modes.Mode;
import dungeonmania.entities.enemy.*;
import dungeonmania.entities.collectable.*;
import dungeonmania.entities.collectable.buildable.Bow;
import dungeonmania.entities.collectable.buildable.BuildableEntity;
import dungeonmania.entities.collectable.buildable.Shield;

public class Player extends Entity implements Damage, Health, Moving{
    private int damage;
    private int maxHealth;
    private int currentHealth;
    private Inventory inventory;
    private int invisibilityDuration;
    private int invincibilityDuration;
    private Sword sword;
    private Armour armour;
    private Bow bow;
    private Shield shield;
    private Direction movement;
    private List<Recipe> recipes;

    public Player(String type, Position position, boolean isInteractable, Mode mode) {
        super(type, position, isInteractable);
        this.damage = 10;
        this.maxHealth = mode.getMaxPlayerHealth();
        this.currentHealth = mode.getMaxPlayerHealth();
        this.inventory = new Inventory();

        RecipeAll allRecipes = new RecipeAll();
        this.recipes = allRecipes.getRecipes();
    }

    public Player(String type, Position position, boolean isInteractable, Mode mode, int damage) {
        this(type, position, isInteractable, mode);
        this.damage = damage;
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

    public int getInvisibilityDuration() {
        return this.invisibilityDuration;
    }

    public void setInvisibilityDuration(int duration) {
        this.invisibilityDuration = duration;
    }

    public int getInvincibilityDuration() {
        return this.invincibilityDuration;
    }

    public void setInvincibilityDuration(int duration) {
        this.invincibilityDuration = duration;
    }

    public boolean hasArmour() {
        for (CollectableEntity collectable : this.inventory.getItems()) {
            if (collectable instanceof Armour) {
                return true;
            }
        }
        return false;
    }

    public boolean hasShield() {
        for (CollectableEntity collectable : this.inventory.getItems()) {
            if (collectable instanceof Shield) {
                return true;
            }
        }
        return false;
    }

    public boolean hasBow() {
        for (CollectableEntity collectable : this.inventory.getItems()) {
            if (collectable instanceof Bow) {
                return true;
            }
        }
        return false;
    }

    public boolean hasSword() {
        for (CollectableEntity collectable : this.inventory.getItems()) {
            if (collectable instanceof Sword) {
                return true;
            }
        }
        return false;
    }

    public int getShieldDefense() {
        return this.shield.getDefense();
    }

    /**
     * if player uses sword, decrease its duration
     * @return damage of sword
     */
    public int useSword() {
        if (hasSword()) {
            int damage = sword.getAttack();
            this.sword.setDurability(this.sword.getDurability() - 1);
            if (this.sword.isBroken()) {
                this.inventory.removeItem(this.sword);
                this.sword = null;
            }
            return damage;
        }
        return 0;
    }

    /**
     * if player uses bow, decrease its duration
     */
    public void useBow() {
        if (hasBow()) {
            this.bow.setDurability(this.bow.getDurability() - 1);
            if (this.bow.isBroken()) {
                this.inventory.removeItem(this.bow);
                this.bow = null;
            }
        }
    }

    @Override
    public void collidesWith(Entity other, Grid grid) {
        if (canMoveInto(other)) {
            if (other instanceof CollectableEntity) {
                collectItem(other, grid);
            } else if (other instanceof Boulder) {
                pushBoulder((Boulder)other, grid);
            } else if (other instanceof Enemy) {
                // enter battle
            } else if (other instanceof Door) {
                // door not open
                if (!((Door)other).getIsOpen()) {
                    // unlock door
                    // inventory.removeNonSpecificItem("key");
                }
            }
        }
    }

    /**
     * Player coolect item and put it to inventory
     */
    public void collectItem(Entity e, Grid grid) {
        List<Entity> entities = grid.getEntities(this.position.getX(), this.position.getY());
        for (Entity entity : entities) {
            if (entity instanceof CollectableEntity) {
                // player can only have one sword, armour, key and buildables
                if (entity instanceof Sword && !hasSword()) {
                    this.inventory.addItem((CollectableEntity)entity);
                    this.sword = (Sword) entity;
                    grid.dettach(entity);
                } else if (entity instanceof Armour && !hasArmour()) {
                    this.inventory.addItem((CollectableEntity)entity);
                    this.armour = (Armour) entity;
                    grid.dettach(entity);
                } else if (entity instanceof Bow && !hasBow()) {
                    this.inventory.addItem((CollectableEntity)entity);
                    this.bow = (Bow) entity;
                    grid.dettach(entity);
                } else if (entity instanceof Shield && !hasShield()) {
                    this.inventory.addItem((CollectableEntity)entity);
                    this.shield = (Shield) entity;
                    grid.dettach(entity);
                } else if (!(entity instanceof Sword && hasSword() ||
                    entity instanceof Shield && hasShield() ||
                    entity instanceof Armour && hasArmour() ||
                    entity instanceof Bow && hasBow())
                ) {
                    this.inventory.addItem((CollectableEntity)entity);
                    grid.dettach(entity);
                }
            }
        }
    }

    /**
     * player pushes boulder
     */
    private void pushBoulder(Boulder boulder, Grid grid) {
        int newX = position.getX() + movement.getOffset().getX();
        int newY = position.getY() + movement.getOffset().getY();

        // detach boulder form old position
        grid.dettach(boulder);

        boulder.setPosition(new Position(newX, newY, boulder.getPosition().getLayer()));

        // attach boulder at new position
        grid.attach(boulder);
    }

    /**
     * check if boulder can be pushed
     */
    public boolean canPushBoulder(Boulder boulder, Grid grid) {
        int x = boulder.getPosition().getX() + movement.getOffset().getX();
        int y = boulder.getPosition().getY() + movement.getOffset().getY();
        if (x >= 0 && x <= grid.getWidth() &&
            y >= 0 && y <= grid.getHeight()
        ) {
            for (Entity entity : grid.getEntities(x, y)) {
                if (!boulder.canMoveInto(entity)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean canMoveInto(Entity other) {
        
        if (other instanceof Wall)                      {return false;} 
        else if (other instanceof Boulder) {
            // //////////////////////////////////////////////////////////////////////
            // check if can push boulder
            return false;
        }
        else if (other instanceof ZombieToastSpawner)   {return false;}
        // else if (other instanceof Door) {
        //     /////////////////////////////////////////////////////////////////////
        //     // if door is unlocked, return true
        //     // if player has the key, return true
        //     // if player doesnt have the key, return false;
            
        // }
        else if (other instanceof Bomb && 
                ((Bomb)other).hasPlaced())              {return false;}
        else                                            {return true;}
    }

    @Override
    public int damageDealt(Entity e) {
        int totalDamageDealt = attack();
        // bow allows player to attack twice
        if (hasBow()) {
            useBow();
            return totalDamageDealt * 2;
        }
        return totalDamageDealt;
    }

    /**
     * damage dealt by player when he attacks
     */
    private int attack() {
        if (hasSword()) {
            return (this.currentHealth * (this.damage + useSword())) / 5;
        } else {
            return (this.currentHealth * this.damage) / 5;
        }
    }

    public void useItem(CollectableEntity e) {
        if (e.isInteractable()) {
            e.useItem(this);
            this.inventory.removeItem(e);
        }
    }

    public void craftItem(String buildable) {
        if (buildable.equals("bow")) {
            if (!hasBow()) {
                Bow bow = new Bow(buildable, new Position(0, 0), false);
                this.bow = bow;
                this.inventory.addItem(bow);
            }
        } else if (buildable.equals("shield")) {
            if (!hasShield()) {
                Shield shield = new Shield(buildable, new Position(0, 0), false);
                this.shield = shield;
                this.inventory.addItem(shield);
            }
        }
    }

    public List<String> getBuildables() {
        List<String> buildables = new ArrayList<>();
        for (Recipe recipe : this.recipes) {
            if (recipe.isCraftable(this.inventory)) {
                buildables.add(type);
            }
        }
        return null;
    }


    @Override
    public void move(Grid grid, Direction d) {
        this.movement = d;
        // check movement within border
        int newX = position.getX() + d.getOffset().getX();
        int newY = position.getY() + d.getOffset().getY();
        if (newX >= 0 && newX <= grid.getWidth() &&
            newY >= 0 && newY <= grid.getHeight()
        ) {
            for (Entity entity : grid.getEntities(newX, newY)) {
                if (entity instanceof Boulder) {
                    if (!canPushBoulder((Boulder) entity, grid)) {
                        return;
                    }
                } else if (!canMoveInto(entity)) {
                    return;
                }
            }

            // player moves
            this.position.translateBy(new Position(newX, newY, this.position.getLayer()));

            // player interacts with entities in the cell
            for (Entity entity : grid.getEntities(newX, newY)) {
                collidesWith(entity, grid);
            }
        }
    }


    @Override
    public boolean movingConstraints(Entity e) {
        return false;
    }


    @Override
    public boolean isDead() {
        if (this.currentHealth <= 0) {
            return true;
        }
        return false;
    }
};

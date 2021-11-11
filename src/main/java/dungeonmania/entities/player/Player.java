package dungeonmania.entities.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import dungeonmania.Grid;
import dungeonmania.entities.Battle;
import dungeonmania.entities.Interaction;
import dungeonmania.entities.Damage;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Health;
import dungeonmania.entities.Moving;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entities.statics.*;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.modes.Mode;
import dungeonmania.entities.enemy.*;
import dungeonmania.entities.collectable.*;
import dungeonmania.entities.collectable.buildable.*;
import dungeonmania.entities.collectable.rarecollectable.*;

public class Player extends Entity implements Damage, Health, Moving {
    private int damage;
    private int maxHealth;
    private int currentHealth;
    private Inventory inventory;
    private StatusEffect statusEffect;
    private Sword sword;
    private Armour armour;
    private Bow bow;
    private Shield shield;
    private Anduril anduril;
    private MidnightArmour midnightArmour;
    private Direction movement;
    private boolean isTeleported;
    private Mercenary mercenary;
    private boolean rareDrop;

    public Player(Position position, Mode mode) {
        super("player", position, false);
        this.maxHealth = mode.getMaxPlayerHealth();
        this.currentHealth = mode.getMaxPlayerHealth();
        this.inventory = new Inventory();
        this.statusEffect = new StatusEffect();
        this.isTeleported = false;
        this.rareDrop = true;
        this.damage = 1;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
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

    public StatusEffect getStatusEffect() {
        return this.statusEffect;
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

    public boolean hasTreasure() {
        for (CollectableEntity collectable : this.inventory.getItems()) {
            if (collectable instanceof Treasure) {
                return true;
            }
        }
        return false;
    }

    public boolean hasSunStone() {
        for (CollectableEntity collectable : this.inventory.getItems()) {
            if (collectable instanceof SunStone) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAnduril() {
        for (CollectableEntity collectable : this.inventory.getItems()) {
            if (collectable instanceof Anduril) {
                return true;
            }
        }
        return false;
    }

    public boolean hasMidnightArmour() {
        for (CollectableEntity collectable : this.inventory.getItems()) {
            if (collectable instanceof MidnightArmour) {
                return true;
            }
        }
        return false;
    }

    public boolean hasMercAlly() {
        if (mercenary != null) {
            return true;
        }
        return false;
    }

    public int getShieldDefense() {
        if (this.hasShield()) {
            return this.shield.getDefense();
        }
        return 0;
    }

    public int getMidnightArmourDefense() {
        if (this.hasMidnightArmour()) {
            return this.midnightArmour.getDefense();
        }
        return 0;
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

    public void useArmour() {
        if (hasArmour()) {
            this.armour.setDurability(this.armour.getDurability() - 1);
            if (this.armour.isBroken()) {
                this.inventory.removeItem(this.armour);
                this.armour = null;
            }
        }
    }

    public void useShield() {
        if (hasShield()) {
            this.shield.setDurability(this.shield.getDurability() - 1);
            if (this.shield.isBroken()) {
                this.inventory.removeItem(this.shield);
                this.shield = null;
            }
        }
    }

    public int useAnduril() {
        if (hasAnduril()) {
            return anduril.getAttack();
        }
        return 0;
    }

    public int useMidnightArmour() {
        if (hasMidnightArmour()) {
            int damage = midnightArmour.getAttack();
            return damage;
        }
        return 0;
    }

    @Override
    public void collidesWith(Entity other, Grid grid) {
        if (canMoveInto(other)) {
            if (other instanceof CollectableEntity) {
                collectItem(other, grid);
            } else if (other instanceof Boulder) {
                pushBoulder((Boulder)other, grid);
            }
            // TODO: uncomment once mercenary state pattern implemented
            // else if (other instanceof BribedMercenary || other instanceof
            // MindCintrolledMercenary) {
            // } 
            else if (other instanceof Enemy) {
                Battle.battle(this, (Enemy)other, grid);
            } else if (other instanceof Door) {
                // door not open
                if (!((Door)other).getIsOpen()) {
                    ((Door)other).setType("door_unlocked");
                    ((Door)other).setIsOpen(true);
                    // remove key
                    if (!hasSunStone()) {
                        Iterator<CollectableEntity> itr = inventory.getItems().iterator();
                        while (itr.hasNext()) {
                            CollectableEntity e = itr.next();
                            if (e instanceof Key) {
                                itr.remove();
                            }
                        }
                    }
                }
            } else if (other instanceof Portal) {
                if (this.isTeleported) {
                    this.isTeleported = false;
                } else if (((Portal)other).getCorrespondingPortal() != null) {
                    teleport((Portal)other, grid);
                    this.isTeleported = true;
                    for (Entity entity : grid.getEntities(this.getPosition().getX(), this.getPosition().getY())) {
                        collidesWith(entity, grid);
                    }
                }
            }
        }
        statusEffect.tickDown();
    }

    /**
     * Player coolect item and put it to inventory
     */
    public void collectItem(Entity e, Grid grid) {
        List<Entity> entities = grid.getEntities(this.getPosition().getX(), this.getPosition().getY());
        for (Entity entity : entities) {
            if (entity instanceof CollectableEntity) {
                if (entity instanceof Key) {
                    boolean hasKey = false;
                    for (CollectableEntity inventoryItem : this.inventory.getItems()) {
                        if (inventoryItem instanceof Key) {
                            hasKey = true;
                        }
                    }
                    if (!hasKey) {
                        this.inventory.addItem((CollectableEntity)entity);
                        grid.dettach(entity);
                    }
                }
                // player can only have one sword, armour, key and buildables
                else if (entity instanceof Sword && !hasSword()) {
                    this.inventory.addItem((CollectableEntity)entity);
                    this.sword = (Sword) entity;
                    grid.dettach(entity);
                } else if (entity instanceof Armour && !hasArmour()) {
                    this.inventory.addItem((CollectableEntity)entity);
                    this.armour = (Armour) entity;
                    grid.dettach(entity);
                } else if (!(entity instanceof Sword && hasSword() ||
                    entity instanceof Armour && hasArmour())
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
        int newX = getPosition().getX() + movement.getOffset().getX();
        int newY = getPosition().getY() + movement.getOffset().getY();

        // set new position of the boulder
        grid.dettach(boulder);
        Position newPosition = new Position(newX, newY, boulder.getPosition().getLayer());
        boulder.setPosition(newPosition);
        grid.attach(boulder);

        // if boulder is on switch, and there's a bomb around the cell, blast.
        for (Entity e : grid.getEntities(newX, newY)) {
            if (e instanceof FloorSwitch) {
                for (Position pos : newPosition.getAdjacentCardinalPositions()) {
                    int x = pos.getX();
                    int y = pos.getY();
                    if (x >= 0 && x < grid.getWidth() &&
                        y >= 0 && y < grid.getHeight()
                    ) {
                        for (Entity entityAround : grid.getEntities(x, y)) {
                            if (entityAround instanceof Bomb && ((Bomb)entityAround).hasPlaced()) {
                                ((Bomb) entityAround).blast(grid);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * check if boulder can be pushed
     */
    public boolean canPushBoulder(Boulder boulder, Grid grid) {
        int x = boulder.getPosition().getX() + movement.getOffset().getX();
        int y = boulder.getPosition().getY() + movement.getOffset().getY();
        if (x >= 0 && x < grid.getWidth() &&
            y >= 0 && y < grid.getHeight()
        ) {
            for (Entity entity : grid.getEntities(x, y)) {
                if (!boulder.canMoveInto(entity)) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * move to position of the corresponding teleport
     */
    private void teleport(Portal portal, Grid grid) {
        int x = portal.getCorrespondingPortal().getPosition().getX();
        int y = portal.getCorrespondingPortal().getPosition().getY();

        grid.dettach(this);
        Position newPosition = new Position(x, y, this.getPosition().getLayer());
        this.setPosition(newPosition);
        grid.attach(this);

        this.isTeleported = true;
    }

    @Override
    public boolean canMoveInto(Entity other) {
        if (other instanceof Wall)                      {return false;}
        else if (other instanceof ZombieToastSpawner)   {return false;}
        else if (other instanceof Door) {
            if (((Door)other).getIsOpen()) {
                return true;
            } else if (!((Door)other).getIsOpen()) {
                if (!hasSunStone()) {
                    int keyNumber = ((Door)other).getKey();
                    for (CollectableEntity e : this.inventory.getItems()) {
                        if (e instanceof Key && ((Key)e).getKeyNumber() == keyNumber) {
                            return true;
                        }
                    }
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }
        else if (other instanceof Bomb && 
                ((Bomb)other).hasPlaced())              {return false;}
        else                                            {return true;}
    }

    @Override
    public int damageDealt() {
        return (getCurrentHealth() * getDamage())/5;
    }

    /**
     * damage dealt by player when he attacks
     */
    // private int attack() {
    //     if (hasSword()) {
    //         return (this.currentHealth * (this.damage + useSword())) / 5;
    //     } else {
    //         return (this.currentHealth * this.damage) / 5;
    //     }
    // }

    public void useItem(String itemId, Grid grid) {
        CollectableEntity collectable = this.inventory.getItemfromId(itemId);
        if (collectable instanceof Bomb) {
            ((Bomb)collectable).placeBomb(this, grid);
        } else if (collectable instanceof HealthPotion) {
            collectable.useItem(this);
            this.inventory.removeItem(collectable);
        } else if (collectable instanceof InvincibilityPotion ||
                   collectable instanceof InvisibilityPotion
        ) {
            collectable.useItemWithEffect(this.statusEffect);
            this.inventory.removeItem(collectable);
        } else if (collectable == null) {
            throw new InvalidActionException("Item is not in inventory.");
        } else {
            throw new IllegalArgumentException("Item cannot be used.");
        }
    }

    public void craftItem(String buildable, Grid grid) {
        if (buildable.equals("bow")) {
            Recipe recipe = getAvailableRecipe(buildable);
            if (recipe == null) {
                throw new InvalidActionException("You do not have sufficient items to craft bow.");
            }
            if (!hasBow()) {
                for (HashMap.Entry<String, Integer> ingredient : recipe.getIngredients().entrySet()) {
                    for (int i = 0; i < ingredient.getValue(); i++) {
                        this.inventory.removeNonSpecificItem(ingredient.getKey());
                    }
                }
                Bow bow = new Bow(new Position(0, 0));
                useIngredient(buildable, recipe);
                this.bow = bow;
                this.inventory.addItem(bow);
            }
        } else if (buildable.equals("shield")) {
            Recipe recipe = getAvailableRecipe(buildable);
            if (recipe == null) {
                throw new InvalidActionException("You do not have sufficient items to craft shield.");
            }
            if (!hasShield()) {
                for (HashMap.Entry<String, Integer> ingredient : recipe.getIngredients().entrySet()) {
                    for (int i = 0; i < ingredient.getValue(); i++) {
                        this.inventory.removeNonSpecificItem(ingredient.getKey());
                    }
                }
                Shield shield = new Shield(new Position(0, 0));
                useIngredient(buildable, recipe);
                this.shield = shield;
                this.inventory.addItem(shield);
            }
        } else if (buildable.equals("sceptre")) {
            Recipe recipe = getAvailableRecipe(buildable);
            if (recipe == null) {
                throw new InvalidActionException("You do not have sufficient items to craft sceptre.");
            }
            useIngredient(buildable, recipe);
            Sceptre sceptre = new Sceptre(new Position(0, 0));
            this.inventory.addItem(sceptre);
        } else if (buildable.equals("midnight_armour")) {
            if (hasZombie(grid)) {
                throw new InvalidActionException("You cannot craft midnight armour when zombie is in game.");
            }
            Recipe recipe = getAvailableRecipe(buildable);
            if (recipe == null) {
                throw new InvalidActionException("You do not have sufficient items to craft midnight armour.");
            }
            useIngredient(buildable, recipe);
            MidnightArmour midnightArmour = new MidnightArmour(new Position(0, 0));
            this.midnightArmour = midnightArmour;
            this.inventory.addItem(midnightArmour);
        }
        else {
            throw new IllegalArgumentException(buildable + " not buildable.");
        }
    }

    private void useIngredient(String buildable, Recipe recipe) {
        for (HashMap.Entry<String, Integer> ingredient : recipe.getIngredients().entrySet()) {
            for (int i = 0; i < ingredient.getValue(); i++) {
                this.inventory.removeNonSpecificItem(ingredient.getKey());
            }
        }
    }

    private boolean hasZombie(Grid grid) {
        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
                for (Entity e : grid.getEntities(x, y)) {
                    if (e instanceof Zombie) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void setMerc(Mercenary mercenary) {
        this.mercenary = mercenary;
    }

    public Mercenary getMerc() {
        return this.mercenary;
    }

    public void setRareDrop(Boolean rareDrop) {
        this.rareDrop = rareDrop;
    }

    public boolean isRareDrop() {
        return this.rareDrop;
    }

    public void setAnduril(Anduril anduril) {
        this.anduril = anduril;
    }

    public List<String> getBuildables() {
        List<String> buildables = new ArrayList<>();
        for (Recipe recipe : this.inventory.getRecipes()) {
            if (!buildables.contains(recipe.getType()) && recipe.isCraftable(this.inventory)) {
                buildables.add(recipe.getType());
            }
        }
        return buildables;
    }

    private Recipe getAvailableRecipe(String buildable) {
        for (Recipe recipe : this.inventory.getRecipes()) {
            if (recipe.getType().equals(buildable) && recipe.isCraftable(this.inventory)) {
                return recipe;
            }
        }
        return null;
    }

    @Override
    public void move(Grid grid, Direction d) {
        this.movement = d;
        // check movement within border
        int newX = getPosition().getX() + d.getOffset().getX();
        int newY = getPosition().getY() + d.getOffset().getY();
        if (newX >= 0 && newX < grid.getWidth() &&
            newY >= 0 && newY < grid.getHeight()
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
            grid.dettach(this);
            this.setPosition(new Position(newX, newY, this.getPosition().getLayer()));
            grid.attach(this);

            // player interacts with entities in the cell
            for (Entity entity : grid.getEntities(newX, newY)) {
                collidesWith(entity, grid);
            }
        }
        statusEffect.tickDown();
    }

    public void interact(String EntityId, Grid grid) {
        Entity entity = null;
        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                for (Entity theEntity : grid.getEntities(i, j)) {
                    if (theEntity.getId().equals(EntityId) && 
                       (theEntity instanceof ZombieToastSpawner || 
                        theEntity instanceof Mercenary)) {
                            entity = theEntity;
                            break;
                    }
                }
            }
        }
        if (entity instanceof Mercenary) {
            Interaction.interactMerc((Mercenary)entity, grid, this);
        } else if (entity instanceof ZombieToastSpawner) {
            Interaction.interactSpawner((ZombieToastSpawner)entity, grid, this);
        } else {
            throw new IllegalArgumentException("entityId is not a valid entity ID");
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

    @Override
    public JSONObject getJSON() {

        JSONObject player = super.getJSON();

        player.put("inventory", inventory.getJSON().get("items"));

        return player;
    }

    @Override
    public void receiveDamage(int damage) {
        setCurrentHealth(getCurrentHealth() - damage);
    }
};

package dungeonmania.entities.player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dungeonmania.Grid;
import dungeonmania.Tick;
import dungeonmania.constants.Layer;
import dungeonmania.entities.Battle;
import dungeonmania.entities.Entity;
import dungeonmania.entities.collectable.Armour;
import dungeonmania.entities.collectable.Bomb;
import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.entities.collectable.Key;
import dungeonmania.entities.collectable.Sword;
import dungeonmania.entities.collectable.buildable.Bow;
import dungeonmania.entities.collectable.buildable.Shield;
import dungeonmania.entities.enemy.Enemy;
import dungeonmania.entities.enemy.Mercenary;
import dungeonmania.entities.statics.Boulder;
import dungeonmania.entities.statics.Door;
import dungeonmania.entities.statics.Portal;
import dungeonmania.entities.statics.Wall;
import dungeonmania.entities.statics.ZombieToastSpawner;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class OlderSelf extends Player{
    private List<Tick> prevTicks = new ArrayList<>();

    public OlderSelf(Player player) {
        super(player);
    }

    public OlderSelf(OlderSelf that, boolean copy) {
        this(that);
        if (copy) {
            for (Tick tick : that.getPrevTicks()) {
                this.prevTicks.add(tick);
            }
        }
    }

    public List<Tick> getPrevTicks() {
        return this.prevTicks;
    }

    public void setPrevTicks(List<Tick> ticks) {
        this.prevTicks = ticks;
    }

    @Override
    public void collidesWith(Entity other, Grid grid) {
        if (canMoveInto(other)) {
            if (other instanceof CollectableEntity) {
                collectItem(other, grid);
            } else if (other instanceof Player) {
                if (!(((Player)other).hasSunStone() || ((Player)other).hasMidnightArmour())) {
                    Battle.battle(this, other, grid);
                }
            }  else if (other instanceof Boulder) {
                pushBoulder((Boulder)other, grid);
            } else if (other instanceof Enemy) {
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
                        collision(grid, getPosition().getX(), getPosition().getY());
                        return;
                    }
                } else if (!canMoveInto(entity)) {
                    collision(grid, getPosition().getX(), getPosition().getY());
                    return;
                }
            }

            // player moves
            grid.dettach(this);
            this.setPosition(new Position(newX, newY, Layer.OLDER_SELF));
            grid.attach(this);

            // player interacts with entities in the cell
            collision(grid, newX, newY);
        }
        statusEffect.tickDown();
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
        else if (other instanceof OlderSelf)            {return false;}
        else                                            {return true;}
    }

    @Override
    public int damageDealt() {
        return (getCurrentHealth() * getDamage())/10;
    }

    @Override
    public OlderSelf clone() {
        return new OlderSelf(this, true);
    }
    
}

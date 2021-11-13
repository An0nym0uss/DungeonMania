package dungeonmania.entities.player;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Grid;
import dungeonmania.Tick;
import dungeonmania.constants.Layer;
import dungeonmania.entities.Entity;
import dungeonmania.entities.collectable.Armour;
import dungeonmania.entities.collectable.Sword;
import dungeonmania.entities.collectable.buildable.Bow;
import dungeonmania.entities.collectable.buildable.Shield;
import dungeonmania.entities.statics.Boulder;
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
            this.setPosition(new Position(newX, newY, Layer.OLDER_SELF));
            grid.attach(this);

            // player interacts with entities in the cell
            for (Entity entity : grid.getEntities(newX, newY)) {
                collidesWith(entity, grid);
            }
        }
        statusEffect.tickDown();
    }

    @Override
    public OlderSelf clone() {
        return new OlderSelf(this, true);
    }
    
}

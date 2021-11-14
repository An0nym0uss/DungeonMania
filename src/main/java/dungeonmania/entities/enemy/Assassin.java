package dungeonmania.entities.enemy;

import dungeonmania.Grid;
import dungeonmania.entities.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entities.enemy.Mercenary;

import java.util.List;
import java.util.*;

public class Assassin extends Mercenary {

    public Assassin(Position position, int speed, int health, int damage) {
        super(position, speed, health, damage);
        this.setType("assassin");
    }

    @Override
    public Assassin clone() {
        Position oldPos = new Position(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getLayer());
        return new Assassin(oldPos, this.getSpeed(), this.getHealth(), this.getDamage());
    }

}

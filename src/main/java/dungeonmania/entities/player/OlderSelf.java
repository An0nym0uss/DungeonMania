package dungeonmania.entities.player;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Tick;
import dungeonmania.constants.Layer;
import dungeonmania.entities.Entity;
import dungeonmania.entities.collectable.Armour;
import dungeonmania.entities.collectable.Sword;
import dungeonmania.entities.collectable.buildable.Bow;
import dungeonmania.entities.collectable.buildable.Shield;
import dungeonmania.util.Position;

public class OlderSelf extends Player{
    private int damage;
    private int maxHealth;
    private int currentHealth;
    private Inventory inventory;
    private StatusEffect statusEffect;
    private Sword sword;
    private Armour armour;
    private Bow bow;
    private Shield shield;
    private dungeonmania.util.Direction movement;
    private boolean isTeleported;
    private List<Tick> prevTicks = new ArrayList<>();

    public OlderSelf(Player player) {
        super(player);
    }
    
}

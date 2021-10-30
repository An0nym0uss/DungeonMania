package dungeonmania.entities;

import dungeonmania.Grid;
import dungeonmania.entities.collectable.rarecollectable.RareCollectableEntities;
import dungeonmania.entities.collectable.rarecollectable.TheOneRing;
import dungeonmania.entities.enemy.Enemy;
import dungeonmania.entities.player.Player;
import dungeonmania.util.Position;

public class Battle {
    public static void battle(Player player, Enemy enemy, Grid grid) {
        if (player.getStatusEffect().isInvisible()) {
            
        } else if (player.getStatusEffect().isInvincible()) {
            grid.dettach(enemy);
        } else {
            // start battle
            // enemy attack first
            int enemyDamageDealt = enemy.damageDealt();
            if (player.hasShield()) {
                enemyDamageDealt = enemyDamageDealt - player.getShieldDefense();
                player.useShield();
            }
            // armour halves enemy damage
            if (player.hasArmour()) {
                enemyDamageDealt = enemyDamageDealt / 2;
                player.useArmour();
            }
            player.setCurrentHealth(player.getCurrentHealth() - enemyDamageDealt);

            if (!player.isDead()){
                //int playerDamageDealt = player.damageDealt();
                ////////////////////////////////////////////////////////////////////////////////////////////////
                // if (enemy.hasArmour) {
                    // playerDamageDealt = playerDamageDealt / 2;
                // }
                // enemy.setHealth(enemy.getHealth() - playerDamageDealt);

                // enemy is dead
                if (enemy.isDead()) {
                    ////////////////////////////////////////////////////////////////////////////////////////////////
                    // if enemy has armour
                    // take the armour if player does not have one
                    grid.dettach(enemy);
                    RareCollectableEntities ring = new TheOneRing(new Position(0, 0));
                    ring.spawnnRareCollectableEntities(player.getInventory());
                }
            } else {
                // player is dead
                // if player has the One Ring, regenerate to full health
                if (player.getInventory().checkItem("the_one_ring") > 0) {
                    player.setCurrentHealth(player.getMaxHealth());
                    player.getInventory().removeNonSpecificItem("the_one_ring");
                } else {
                    // player dies   
                    grid.dettach(player);
                }
            }
        }
    }
}

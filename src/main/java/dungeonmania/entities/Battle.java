package dungeonmania.entities;

import dungeonmania.Grid;
import dungeonmania.entities.collectable.rarecollectable.Anduril;
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
            player.receiveDamage(enemyDamageDealt);

            if (!player.isDead()){
                int playerDamageDealt = player.damageDealt();
                //////////////////////////////////////////////////////////////////////////////////////////////
                // if (enemy.hasArmour) {
                //     playerDamageDealt = playerDamageDealt / 2;
                // }
                enemy.receiveDamage(playerDamageDealt);

                if (enemy.isDead()) {
                    ////////////////////////////////////////////////////////////////////////////////////////////////
                    grid.dettach(enemy);
                    RareCollectableEntities.spawnnRareCollectableEntities(player.getInventory());
                    // RareCollectableEntities ring = new TheOneRing();
                    // ring.spawnnOneRing(player.getInventory());
                    // RareCollectableEntities anduril = new Anduril();
                    // anduril.spawnnAnduril(player.getInventory());
                }
            } else {
                // player is dead
                // if player has the One Ring, regenerate to full health
                if (player.getInventory().checkItem("one_ring") > 0) {
                    player.setCurrentHealth(player.getMaxHealth());
                    player.getInventory().removeNonSpecificItem("one_ring");
                    grid.dettach(enemy);
                } else {
                    // player dies   
                    grid.dettach(player);
                }
            }
        }
    }
}

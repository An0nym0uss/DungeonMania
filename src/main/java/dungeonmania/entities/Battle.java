package dungeonmania.entities;

import dungeonmania.Grid;
import dungeonmania.entities.collectable.rarecollectable.Anduril;
import dungeonmania.entities.collectable.rarecollectable.RareCollectableEntities;
import dungeonmania.entities.collectable.rarecollectable.TheOneRing;
import dungeonmania.entities.enemy.Enemy;
import dungeonmania.entities.enemy.Hydra;
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
            int enemyDamageDealt = enemyDamage(player, enemy);
            player.receiveDamage(enemyDamageDealt);

            if (!player.isDead()){
                if (!player.hasAnduril()) {
                    int playerDamageDealt = playerDamage(player, enemy);
                    enemy.receiveDamage(playerDamageDealt);
                    // player with bow attack again
                    if (player.hasBow() && !enemy.isDead()) {
                        enemy.receiveDamage(playerDamageDealt);
                    }
                    // if player has an ally mercenary, it will attack as well
                    int mercDamageDealt = MercDamage(player, enemy);
                    enemy.receiveDamage(mercDamageDealt);
                } else {
                    int playerDamageDealt = playerDamage(player, enemy);
                    enemy.receiveAndruilDamage(playerDamageDealt);
                    // if player has a bow and and an anduril, the second attack 
                    // with the bow will still count as an attack with andruil
                    if (player.hasBow() && !enemy.isDead()) {
                        enemy.receiveAndruilDamage(playerDamageDealt);
                    }
                    // mecenary attack does not count as an attack with anduril
                    int mercDamageDealt = MercDamage(player, enemy);
                    enemy.receiveDamage(mercDamageDealt);

                }

                if (enemy.isDead()) {
                    grid.dettach(enemy);
                    RareCollectableEntities.spawnnRareCollectableEntities(player);
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

    private static int playerDamage(Player player, Enemy enemy) {
        // calculate character base damage
        int damage =  (player.getCurrentHealth() * player.getDamage())/5;

        // add weapon damage if player has any weapon
        damage += player.useSword();
        damage += player.useAnduril();

        // player with anduril deal triple damage against bosses
        if (player.hasAnduril() && (enemy instanceof Hydra /* || instanceof Assassin*/)) {
            damage *= 3;
        }
        // TODO: uncomment once zombie with armour is inplemented
        // if (enemy instanceof Zombie && (Zombie)enemy.hasArmour()) {
        //     playerDamageDealt = playerDamageDealt / 2;
        // }
        return damage;
    }

    private static int MercDamage(Player player, Enemy enemy) {
        int damage = 0;
        if (player.hasMercAlly()) {
            // mercenary as an ally also use character's damage formula
            damage = (player.getMerc().getHealth() * player.getMerc().getDamage())/ 5;
        }
        // TODO: uncomment once zombie with armour is inplemented
        // if (enemy instanceof Zombie && (Zombie)enemy.hasArmour()) {
        //     playerDamageDealt = playerDamageDealt / 2;
        // }
        return damage;
    }

    private static int enemyDamage(Player player, Enemy enemy) {
        int damage = enemy.damageDealt();
        // damage is deducted by the shield's defense if layer have one 
        damage = damage - player.getShieldDefense();
        player.useShield();
        // armour halves enemy damage
        if (player.hasArmour()) {
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

# Assumptions

## `Mode`
* Can still use invincibility potion in hard mode, but does nothing.
* We set our own standard player health and hard player health.

## `Dungeon`
* We allow for the dungeon to become unsolvable, e.g, blow up exit with a bomb.

## `Player`
* Player can only hold one sword, one bow and one shield.

## `Entities`
* Enemies entities cannot spawn on the grid where collectable entities are placed on the grid.
* Battles are simultaneous, so even if a player dies during a battle, it still does damage to the enemy. This is only important if a player respawns.
* Entities cannot move into bomb.
* Each moving entity have their own individual damage and health.

## `Boulders`
* Boulders can be pushed into exit, open doors, and portals, blocking anyone who tries to enter.

## `Grid`
* Entities cannot move out of the bound of the grid. By default if not specified, the grid is 15x15 in size.
* Grid has layers, 5 layers, one for static entities, collectables, moving enitiies (including boulders), spiders, and player.
* Entities in the same layer cannot move into each other.

## `.json`
* The json file should always have a key called entities with a corresponding json array value with it.
* If the json file is formatted correctly, then it should always have entities stored as the correct format.
* Assumed that entity names that are more than one work have underscores between it word, such as zombie_toast_spawner.

## `Mercenary`
* Mercenary only needs one coin to be bribed.
* If the mercenary cannot move such that its closer to the player, then it doesn't move at all.

## `Potion effects`
* When the player use invincibility potion or invisibility potion when their effect is still on, their duration will reset.

## `Key`
* If a key is used to craft a shield, it is removed from the inventory. Yes, you cannot make the game unsolvable if you craft a key instead of using it on a door.

## `One Ring`
* When one ring is used, player heal back to full health and kill the current enemy.

## `Bomb`
* Bomb is in the same layer as enemies, so it can be placed on exit, switch, portal, and open door.
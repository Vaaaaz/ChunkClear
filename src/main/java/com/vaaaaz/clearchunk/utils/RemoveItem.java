package com.vaaaaz.clearchunk.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.Material.AIR;

public class RemoveItem {

    public static void removeItemAmount(Inventory inventory, ItemStack item, int amount) {
        if(item.getAmount() <= amount) {
            inventory.removeItem(item);
            return;
        }
        item.setAmount(item.getAmount() - amount);
    }

    public static void removeItemFromHand(Player player, int amount) {
        ItemStack item = player.getItemInHand();
        if (item.getAmount() > amount) {
            item.setAmount(item.getAmount() - amount);
        } else {
            player.setItemInHand(new ItemStack(AIR));
        }
    }
    public static void removeItemFromHand(Player player) {
        removeItemFromHand(player, 1);
    }


}

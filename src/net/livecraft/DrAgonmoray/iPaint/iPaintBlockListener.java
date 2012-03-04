package net.livecraft.DrAgonmoray.iPaint;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;

public class iPaintBlockListener implements Listener {

    public static iPaint plugin;

    public iPaintBlockListener(iPaint instance) {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockDamage(BlockDamageEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        if (block.getType().equals(Material.AIR)) return;
        if (plugin.painters.containsKey(player)) {
            ItemStack item = player.getItemInHand();
            if (!item.getType().equals(Material.AIR) && item.getTypeId() <= 96) {
                if (!item.getType().equals(block.getType())) {
                    block.setType(item.getType());
                    block.setData((byte) item.getDurability());
                } else {
                    if (item.getDurability() != block.getData()) {
                        block.setData((byte) item.getDurability());
                    } else {
                        return;
                    }
                }
                if (plugin.canPaint(player) == 1) {
                    if (item.getAmount() == 1) {
                        player.getInventory().remove(item);
                    } else {
                        item.setAmount(item.getAmount() - 1);
                    }
                }
            }
        }
    }
}
package cn.enaium.treecutter;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Enaium
 */
public class Events implements Listener {

    private final List<Material> logs = Arrays.asList(Material.WARPED_STEM, Material.CRIMSON_STEM, Material.OAK_LOG, Material.BIRCH_LOG, Material.SPRUCE_LOG, Material.JUNGLE_LOG, Material.DARK_OAK_LOG, Material.ACACIA_LOG);
    private final List<Material> leaves = Arrays.asList(Material.NETHER_WART_BLOCK, Material.WARPED_WART_BLOCK, Material.OAK_LEAVES, Material.BIRCH_LEAVES, Material.SPRUCE_LEAVES, Material.JUNGLE_LEAVES, Material.DARK_OAK_LEAVES, Material.ACACIA_LEAVES);
    private final List<Material> target = new ArrayList<>();
    private final List<Material> tools = Arrays.asList(Material.NETHERITE_AXE, Material.DIAMOND_AXE, Material.GOLDEN_AXE, Material.IRON_AXE, Material.STONE_AXE, Material.WOODEN_AXE);

    private final JavaPlugin javaPlugin;

    public Events(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        target.clear();
        if (javaPlugin.getConfig().getBoolean("log")) {
            target.addAll(logs);
        }

        if (javaPlugin.getConfig().getBoolean("leave")) {
            target.addAll(leaves);
        }


        final Block block = event.getBlock();
        final Player player = event.getPlayer();
        if (!logs.contains(block.getBlockData().getMaterial())) return;
        ItemStack item = player.getInventory().getItem(player.getInventory().getHeldItemSlot());
        if (item == null) return;
        if (item.getData() == null) return;
        if (!tools.contains(item.getType())) return;
        if (!event.getPlayer().isSneaking()) return;
        breakNaturally(block, player, item);
    }

    private void breakNaturally(Block block, Player player, ItemStack hand) {
        //x -1 0 1
        //y -1 0 1
        //z -1 0 1
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                for (int z = -1; z < 2; z++) {
                    Block relative = block.getRelative(x, y, z);
                    if (target.contains(relative.getBlockData().getMaterial())) {

                        ItemMeta meta = hand.getItemMeta();
                        if (meta != null) {
                            int damage = ((Damageable) hand.getItemMeta()).getDamage() + 1;
                            ((Damageable) meta).setDamage(damage);

                            if (damage <= hand.getType().getMaxDurability()) {
                                if (player.getGameMode() != GameMode.CREATIVE) {
                                    hand.setItemMeta(meta);
                                }

                                relative.breakNaturally();
                                breakNaturally(relative, player, hand);
                            }
                        }
                    }
                }
            }
        }
    }
}

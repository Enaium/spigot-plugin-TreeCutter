package cn.enaium.treecutter;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Enaium
 */
public class Events implements Listener {

    private final List<String> logs = LLTMap.logs;
    private final List<String> leaves = LLTMap.leaves;
    private final List<String> target = new ArrayList<>();
    private final List<String> tools = LLTMap.tools;

    private final JavaPlugin javaPlugin;

    public Events(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        if (!javaPlugin.getConfig().getBoolean("enable")) return;

        target.clear();
        if (javaPlugin.getConfig().getBoolean("log")) {
            target.addAll(logs);
        }

        if (javaPlugin.getConfig().getBoolean("leave")) {
            target.addAll(leaves);
        }

        final Block block = event.getBlock();
        final Player player = event.getPlayer();

        if (!logs.contains(block.getType().name())) return;
        ItemStack item = player.getInventory().getItem(player.getInventory().getHeldItemSlot());
        if (item == null) return;
        if (item.getData() == null) return;
        if (!tools.contains(item.getType().name())) return;
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
                    if (target.contains(relative.getType().name())) {
                        ItemMeta meta = hand.getItemMeta();
                        if (meta != null) {
                            short damage = (short) (hand.getDurability() + 1);

                            if (damage <= hand.getType().getMaxDurability()) {
                                if (player.getGameMode() != GameMode.CREATIVE) {
                                    hand.setDurability(damage);
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

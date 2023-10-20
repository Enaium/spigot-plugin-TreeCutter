package cn.enaium.treecutter;

import java.util.List;

public interface LLTMap {
    List<String> logs = TreeCutter.INSTANCE.getConfig().getStringList("list.logs");
            //Arrays.asList("WARPED_STEM", "CRIMSON_STEM", "OAK_LOG", "BIRCH_LOG", "SPRUCE_LOG", "JUNGLE_LOG", "DARK_OAK_LOG", "ACACIA_LOG")
    List<String> leaves = TreeCutter.INSTANCE.getConfig().getStringList("list.leaves");
            //Arrays.asList("NETHER_WART_BLOCK", "WARPED_WART_BLOCK", "OAK_LEAVES", "BIRCH_LEAVES", "SPRUCE_LEAVES", "JUNGLE_LEAVES", "DARK_OAK_LEAVES", "ACACIA_LEAVES");
    List<String> tools = TreeCutter.INSTANCE.getConfig().getStringList("list.tools");
            //Arrays.asList("NETHERITE_AXE", "DIAMOND_AXE", "GOLDEN_AXE", "IRON_AXE", "STONE_AXE", "WOODEN_AXE");

}

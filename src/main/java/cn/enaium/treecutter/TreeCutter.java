package cn.enaium.treecutter;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class TreeCutter extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "TreeCutter is enable");
        getServer().getPluginManager().registerEvents(new EventHandles(), this);
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "TreeCutter is disable");
    }
}

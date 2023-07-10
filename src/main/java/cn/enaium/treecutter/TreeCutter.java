package cn.enaium.treecutter;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class TreeCutter extends JavaPlugin {

    static Plugin INSTANCE = null;

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new Events(this), this);
        getLogger().log(Level.INFO, "TreeCutter is enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "TreeCutter is disabled.");
    }
}

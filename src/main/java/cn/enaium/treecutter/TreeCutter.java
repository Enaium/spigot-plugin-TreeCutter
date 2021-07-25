package cn.enaium.treecutter;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;

public final class TreeCutter extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "TreeCutter is enable");
        getServer().getPluginManager().registerEvents(new Events(this), this);
    }

    @Override
    public void onDisable() {
        saveConfig();
        getLogger().log(Level.INFO, "TreeCutter is disable");
    }
}

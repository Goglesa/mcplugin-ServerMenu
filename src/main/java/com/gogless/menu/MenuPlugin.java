package com.gogless.menu;

import org.bukkit.plugin.java.JavaPlugin;

public final class MenuPlugin extends JavaPlugin {

    private static MenuPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        // Register command
        getCommand("menu").setExecutor(new MenuCommand());
        // Register listener
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getLogger().info("ServerMenu plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("ServerMenu plugin has been disabled.");
    }

    public static MenuPlugin getInstance() {
        return instance;
    }
}

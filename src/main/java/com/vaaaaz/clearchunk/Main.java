package com.vaaaaz.clearchunk;

import com.vaaaaz.clearchunk.commands.GiveClearChunk;
import com.vaaaaz.clearchunk.events.PlayerInteract;
import com.vaaaaz.clearchunk.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {


    public static Main plugin;

    public static ConfigUtils config;


    @Override
    public void onEnable() {
        plugin = this;

        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(" §bVazClearChunk - 1.0");
        Bukkit.getConsoleSender().sendMessage("  §aINICIADO COM SUCESSO!");
        Bukkit.getConsoleSender().sendMessage("");


        archives();
        registerCommands();
        registerEvents();

    }

    @Override
    public void onDisable() {


    }

    public void registerCommands() {
        getCommand("clearchunk").setExecutor(new GiveClearChunk());
    }

    public void registerEvents() {

        Bukkit.getPluginManager().registerEvents(new PlayerInteract(), this);

    }

    public void archives() {

        config = new ConfigUtils(this, "config.yml");

        config.saveDefaultConfig();

        createFile(this, "", false);

    }

    public void createFile(Main main, String fileName, boolean isFile) {
        try {
            File file = new File(main.getDataFolder() + File.separator + fileName);
            if (isFile) file.createNewFile();
            else if (!file.exists()) file.mkdirs();
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }


}
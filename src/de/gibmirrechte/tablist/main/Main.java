package de.gibmirrechte.tablist.main;

import de.gibmirrechte.tablist.listener.JoinListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {

    public static String stringHeader = "";
    public static String stringFooter = "";
    public static int interval = 0;

    File configFile = new File("plugins//Tablist//config.yml");
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);

    @Override
    public void onEnable() {
        if (!configFile.exists()) {
            stringHeader = "&7You need help? &9https://discord.gg/NPT6NcFQ5Z";
            stringFooter = "&6This is an example.&nYou can use multiple lines!";
            interval = 60000;

            yamlConfiguration.set("Header", "&7You need help? &9https://discord.gg/NPT6NcFQ5Z");
            yamlConfiguration.set("Footer", "&6This is an example.");
            yamlConfiguration.set("Interval", 60000);
            try{
                yamlConfiguration.save(configFile);
            }catch (IOException ioException){
                ioException.printStackTrace();
            }
        }

        stringHeader = yamlConfiguration.getString("Header");
        stringFooter = yamlConfiguration.getString("Footer");
        interval = yamlConfiguration.getInt("Interval");

        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);

        System.out.println("============= TABLIST =============");
        System.out.println("");
        System.out.println("Successfully loaded");
        System.out.println("Version: " + this.getDescription().getVersion());
        System.out.println("Developer: " + this.getDescription().getAuthors());
        System.out.println("Discord: https://discord.gg/NPT6NcFQ5Z");
        System.out.println("");
        System.out.println("============= TABLIST =============");
    }

    @Override
    public void onDisable() {
        System.out.println("============= TABLIST =============");
        System.out.println("");
        System.out.println("Successfully unloaded");
        System.out.println("Version: " + this.getDescription().getVersion());
        System.out.println("Developer: " + this.getDescription().getAuthors());
        System.out.println("Discord: https://discord.gg/NPT6NcFQ5Z");
        System.out.println("");
        System.out.println("============= TABLIST =============");
    }
}

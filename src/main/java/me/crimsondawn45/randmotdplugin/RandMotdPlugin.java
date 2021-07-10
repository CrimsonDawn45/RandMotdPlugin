package me.crimsondawn45.randmotdplugin;

import org.bukkit.plugin.java.JavaPlugin;

import me.crimsondawn45.datafileplugin.DataFile;
import me.crimsondawn45.randmotdplugin.commands.MotdCommand;
import me.crimsondawn45.randmotdplugin.commands.MotdTab;
import me.crimsondawn45.randmotdplugin.events.ServerPingEvent;

public class RandMotdPlugin extends JavaPlugin {

    public DataFile motd;

    @Override
    public void onEnable() {

        //Initialize Datafile
        this.motd = new DataFile("motd", this);

        //Register Event
        getServer().getPluginManager().registerEvents(new ServerPingEvent(), this);

        //Register Commands
        this.getCommand("motd").setExecutor(new MotdCommand());
        this.getCommand("motd").setTabCompleter(new MotdTab());
       
    }

    @Override
    public void onDisable() {

    }
}
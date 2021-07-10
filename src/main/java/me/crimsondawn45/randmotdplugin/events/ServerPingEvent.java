package me.crimsondawn45.randmotdplugin.events;

import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import me.crimsondawn45.datafileplugin.DataFile;
import me.crimsondawn45.datafileplugin.DataFilePlugin;

public class ServerPingEvent implements Listener {

    public final String DEFAULT_MOTD = "A Minecraft Server";
    
    @EventHandler
    public void onPing(ServerListPingEvent event) {
        
        DataFile motd = DataFilePlugin.getDataFile("motd");
        FileConfiguration config = motd.getConfig();
        String mainMotd;
        String randMotd = "";
        
        //Resolve main motd
        if(config.contains("motd.main")) {
            mainMotd = ChatColor.translateAlternateColorCodes('&', config.getString("motd.main").trim());
        } else {
            mainMotd = DEFAULT_MOTD;
            config.set("motd.main", DEFAULT_MOTD);
            motd.save(config);
        }

        //Resolve random motd
        if(config.contains("motd.random")) {
            List<String> randMotdList = config.getStringList("motd.random");
            Random random = new Random();
            randMotd = ChatColor.translateAlternateColorCodes('&', randMotdList.get(random.nextInt(randMotdList.size())));
        }

        event.setMotd(mainMotd + "\n" + randMotd);
    }
}
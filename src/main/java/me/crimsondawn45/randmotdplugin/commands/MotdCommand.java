package me.crimsondawn45.randmotdplugin.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.crimsondawn45.datafileplugin.DataFile;
import me.crimsondawn45.datafileplugin.DataFilePlugin;

public class MotdCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {

            Player player = (Player) sender;

            if(player.isOp() || player.hasPermission("motd.change")) {
                handleCommand(sender, command, label, args, DataFilePlugin.getDataFile("motd"));
                return true;
            } else {
                player.sendMessage(ChatColor.RED + "You must be an operator to use this command!");
                return true;
            }
        } else {
            handleCommand(sender, command, label, args, DataFilePlugin.getDataFile("motd"));
            return true;
        }
    }

    //Handle command after permission checks
    private static void handleCommand(CommandSender sender, Command command, String label, String[] args, DataFile motd) {

        if(args.length > 0) {
            String option = args[0].toLowerCase();

            switch(option) {

                case("add"):
                    addMotd(sender, buildMotd(args), motd);
                    break;
                case("remove"):
                    removeMotd(sender, buildMotd(args), motd);
                    break;
                case("main"):
                    setMain(sender, buildMotd(args), motd);
                    break;
                default:
                    sender.sendMessage(ChatColor.RED + "Invalid option please use \"add\", \"remove\", or \"main\"");
                    break;
            }
        } else {
            sender.sendMessage("Usage: /motd [add | remove | main] <motd>");
        }
    }

    //Combine all args after first one into a motd
    private static String buildMotd(String[] args) {

        String result = "";

        for(int i = 1; i < args.length; i++) {
            result = result + " " + args[i];
        }

        return result.trim();
    }

    //Handle adding the motd
    private static void addMotd(CommandSender sender, String motd, DataFile motdFile) {

        FileConfiguration config = motdFile.getConfig();

        if(config.contains("motd.random")) {
            List<String> randMotdList = config.getStringList("motd.random");
            randMotdList.add(motd);
            config.set("motd.random", randMotdList);
            motdFile.save(config);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAdded new random motd:&r \"" + motd + "&r\"."));
        } else {
            List<String> randMotdList = new ArrayList<String>();
            randMotdList.add(motd);
            config.set("motd.random", randMotdList);
            motdFile.save(config);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAdded new random motd:&r \"" + motd + "&r\"."));
        }
    }

    //Handle removing a motd
    private static void removeMotd(CommandSender sender, String motd, DataFile motdFile) {

        FileConfiguration config = motdFile.getConfig();

        if(config.contains("motd.random")) {
            List<String> randMotdList = config.getStringList("motd.random");

            if(randMotdList.contains(motd)) {

                randMotdList.remove(motd);
                config.set("motd.random", randMotdList);
                motdFile.save(config);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSuccessfully removed motd:&r \"" + motd + "&r\"."));

            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNo such motd:&r \"" + motd + "&r\"."));
            }

        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNo such motd:&r \"" + motd + "&r\"."));
        }
    }

    //Handle setting main motd
    private static void setMain(CommandSender sender, String motd, DataFile motdFile) {

        FileConfiguration config = motdFile.getConfig();

        if(motd != "") {
            config.set("motd.main", motd);
            motdFile.save(config);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSet main motd to:&r \"" + motd + "&r\"."));
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aCurrent main motd is:&r \"" + config.getString("motd.main") + "&r\"."));
        }
    }
}

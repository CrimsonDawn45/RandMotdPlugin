package me.crimsondawn45.randmotdplugin.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class MotdTab implements TabCompleter {

    List<String> arguments = new ArrayList<String>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if(arguments.isEmpty()) {
            arguments.add("main");
            arguments.add("add");
            arguments.add("remove");
        }

        List<String> result = new ArrayList<>();

        //Handle First Argument
        if(args.length == 1) {
            for(String entry : arguments) {
                if(entry.startsWith(args[0].toLowerCase())) {
                    result.add(entry);
                }
            }
            return result;
        }
        return null;
    }
}


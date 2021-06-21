package net.gigaclub.buildersystemplugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TapBSC implements TabCompleter {


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 2){
            List<String> arguments = new ArrayList<>();
            arguments.add("Normal");
            arguments.add("Void");
            arguments.add("Flat");

            return arguments;
        }
        return null;
    }
}



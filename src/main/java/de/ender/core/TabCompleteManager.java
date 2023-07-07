package de.ender.core;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Deprecated
public class TabCompleteManager implements TabCompleter {

    private final HashMap<Integer,String[]> argsComps = new HashMap<>();

    public TabCompleteManager addArgsXComps(int argsX, String[] completes ){
        argsComps.put(argsX+1,completes);
        return this;
    }

    //add permissions later//

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();

        for(int i=0;i<=argsComps.size();i++) {
            if(args.length == i) {
                String[] argsi = argsComps.get(i);
                if(argsi != null) {
                    Collections.addAll(commands, argsi);
                }
            }
        }

        StringUtil.copyPartialMatches(args[args.length-1], commands, completions); //copy matches of first argument
        Collections.sort(completions);//sort the list
        return completions;
    }
}

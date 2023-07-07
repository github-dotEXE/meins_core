package de.ender.core;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Supplier;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    private final HashMap<Integer,List<String>> checksI = new HashMap<>();
    private final HashMap<Integer,Supplier<ArrayList<String>>> checksIS = new HashMap<>();
    private final HashMap<String,List<String>> checksX = new HashMap<>();
    private final HashMap<String,Supplier<ArrayList<String>>> checksXS = new HashMap<>();

    public TabCompleter addCompI(int i,String... comps){
        checksI.put(i,Arrays.asList(comps));
        return this;
    }
    public TabCompleter addCompI(int i, Supplier<ArrayList<String>> supplier){
        checksIS.put(i,supplier);
        return this;
    }
    public TabCompleter addCompX(String x,String... comps){
        checksX.put(x,Arrays.asList(comps));
        return this;
    }
    public TabCompleter addCompX(String x, Supplier<ArrayList<String>> supplier){
        checksXS.put(x,supplier);
        return this;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();

        commands.addAll(checksI.getOrDefault(args.length,new ArrayList<>()));
        commands.addAll(checksIS.getOrDefault(args.length, ArrayList::new).get());
        if(args.length >=2) {
            commands.addAll(checksX.getOrDefault(args[args.length - 2], new ArrayList<>()));
            commands.addAll(checksXS.getOrDefault(args[args.length - 2], ArrayList::new).get());
        }

        StringUtil.copyPartialMatches(args[args.length-1], commands, completions); //copy matches of first argument

        Collections.sort(completions);//sort the list
        return completions;
    }
}

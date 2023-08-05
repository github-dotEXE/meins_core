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
    private final HashMap<String,List<String>> checksXI = new HashMap<>();
    private final HashMap<String,Integer> checksIX = new HashMap<>();
    private final HashMap<String, Supplier<ArrayList<String>>> checksXIS = new HashMap<>();
    private final HashMap<String,Integer> checksIXS = new HashMap<>();

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
    public TabCompleter addCompXI(String x,int i, String... comps){
        checksXI.put(x, Arrays.asList(comps));
        checksIX.put(x,i);
        return this;
    }
    public TabCompleter addCompXI(String x,int i, Supplier<ArrayList<String>> supplier){
        checksXIS.put(x, supplier);
        checksIXS.put(x,i);
        return this;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();
        int i=args.length-1;

        commands.addAll(checksI.getOrDefault(args.length,new ArrayList<>()));
        commands.addAll(checksIS.getOrDefault(args.length, ArrayList::new).get());
        if(i >=1) {
            String x = args[i-1];
            commands.addAll(checksX.getOrDefault(x, new ArrayList<>()));
            commands.addAll(checksXS.getOrDefault(x, ArrayList::new).get());

            if(checksIX.get(x)==i) commands.addAll(checksXI.getOrDefault(x, new ArrayList<>()));
            if(checksIXS.get(x)==i) commands.addAll(checksXIS.getOrDefault(x, ArrayList::new).get());
        }

        StringUtil.copyPartialMatches(args[args.length-1], commands, completions); //copy matches of first argument

        Collections.sort(completions);//sort the list
        return completions;
    }
}

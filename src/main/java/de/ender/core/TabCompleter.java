package de.ender.core;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;
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
    private final HashMap<String,List<String>> checksPath = new HashMap<>();
    private final HashMap<String, Supplier<ArrayList<String>>> checksPathS = new HashMap<>();
    private final HashMap<List<String>,List<String>> checksMultiPath = new HashMap<>();
    private final HashMap<Supplier<ArrayList<String>>,List<String>> checksMultiPathS = new HashMap<>();
    private final HashMap<Predicate<Integer>,List<String>> checksPredicate = new HashMap<>();
    private final HashMap<Predicate<Integer>,Supplier<ArrayList<String>>> checksPredicateS = new HashMap<>();


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
    public TabCompleter addPathedComp(String path, String... comps){
        checksPath.put(path, Arrays.asList(comps));
        return this;
    }
    public TabCompleter addPathedComp(String path, Supplier<ArrayList<String>> supplier){
        checksPathS.put(path, supplier);
        return this;
    }
    public TabCompleter addMultiPathedComp(String comp,String... paths){
        checksMultiPath.put(Collections.singletonList(comp),Arrays.asList(paths));
        return this;
    }
    public TabCompleter addMultiPathedComp(String[] comps,String... paths){
        checksMultiPath.put(Arrays.asList(comps),Arrays.asList(paths));
        return this;
    }
    public TabCompleter addMultiPathedComp(Supplier<ArrayList<String>> supplier,String... paths){
        checksMultiPathS.put(supplier, Arrays.asList(paths));
        return this;
    }
    public TabCompleter addPredicateComp(Predicate<Integer> shouldAdd, String... comps){
        checksPredicate.put(shouldAdd,Arrays.asList(comps));
        return this;
    }
    public TabCompleter addPredicateComp(Predicate<Integer> shouldAdd, Supplier<ArrayList<String>> supplier){
        checksPredicateS.put(shouldAdd,supplier);
        return this;
    }
    private static String[] getXArgs(String[] xarray,String[] args){
        List<String> xarraylist = new ArrayList<>(Arrays.asList(xarray));
        List<String> argslist = new ArrayList<>(Arrays.asList(args));

        while (xarraylist.contains("x")){
            argslist.remove(xarraylist.indexOf("x"));
            xarraylist.remove("x");
        }
        return argslist.toArray(new String[0]);
    }

    private static boolean isPath(String path,String[] args){
        String[] ppath = path.split("\\.");
        List<String> ppathlist = new ArrayList<>(Arrays.asList(ppath));
        ppathlist.removeIf(i->Objects.equals(i,"x"));
        String[] xppath = ppathlist.toArray(new String[0]);

        if(args.length-1<ppath.length) {
            return false;
        }
        String[] xargs = getXArgs(ppath,Arrays.copyOf(args, args.length - 1));

        //Bukkit.broadcastMessage(Arrays.toString(xargs)+"  "+ Arrays.toString(args) +"  "+ Arrays.toString(xppath)+"  "+ Arrays.toString(ppath));
        return Arrays.equals(xargs, xppath);
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();
        int i=args.length-1;

        commands.addAll(checksI.getOrDefault(i,new ArrayList<>()));
        commands.addAll(checksIS.getOrDefault(i, ArrayList::new).get());
        if(i >=1) {
            String x = args[i-1];
            commands.addAll(checksX.getOrDefault(x, new ArrayList<>()));
            commands.addAll(checksXS.getOrDefault(x, ArrayList::new).get());

            if(checksIX.getOrDefault(x,-1)==i) commands.addAll(checksXI.getOrDefault(x, new ArrayList<>()));
            if(checksIXS.getOrDefault(x,-1)==i) commands.addAll(checksXIS.getOrDefault(x, ArrayList::new).get());
        }
        checksPath.forEach((path,list)->{
            if(isPath(path,args)) commands.addAll(list);
        });
        checksPathS.forEach((path,supplier)->{
            if(isPath(path,args)) commands.addAll(supplier.get());
        });

        checksMultiPath.forEach((list,paths)->{
            for (String path : paths) {
                if (isPath(path,args)) {
                    commands.addAll(list);
                    break;
                }
            }
        });
        checksMultiPathS.forEach((supplier,paths)->{
            for (String path : paths) {
                if (isPath(path,args)) {
                    commands.addAll(supplier.get());
                    break;
                }
            }
        });
        checksPredicate.forEach((shouldAdd,list)->{
            if(shouldAdd.test(i)) commands.addAll(list);
        });
        checksPredicateS.forEach((shouldAdd,supplier)->{
            if(shouldAdd.test(i)) commands.addAll(supplier.get());
        });

        StringUtil.copyPartialMatches(args[i], commands, completions); //copy matches of first argument

        Collections.sort(completions);//sort the list
        return completions;
    }
}

package de.ender.core;

import java.util.Arrays;

public class Colorer {
    public static String string(String str,String index){
        return String.join(" ", str.replace(index, "§"));
    }
    public static String string(String str){
        return string(str,"&");
    }
    public static String list(String[] lst,String index){
        return String.join(" ", Arrays.copyOfRange(lst, 0, lst.length)).replace(index, "§");
    }
    public static String list(String[] lst){
        return list(lst,"&");
    }
}

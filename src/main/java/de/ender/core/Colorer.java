package de.ender.core;

import java.util.Arrays;

@Deprecated
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
    public static String removeString(String str){
        while (str.contains("§")){
            str = str.substring(str.indexOf("§")+2);
        }
        return str;
    }
}

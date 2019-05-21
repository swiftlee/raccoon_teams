package com.phaseos.util;

import org.bukkit.ChatColor;

public class StringUtils {
    public static String fmt(String txt) {
        return ChatColor.translateAlternateColorCodes('&', txt);
    }
}

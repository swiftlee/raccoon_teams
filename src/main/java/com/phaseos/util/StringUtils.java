package com.phaseos.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.Objects;

public class StringUtils {
    public static String fmt(String txt) {
        return ChatColor.translateAlternateColorCodes('&', txt);
    }

    public static Location locationFromStr(String location) {
        String[] data = location.split(",");
        if (data.length == 4)
            return new Location(Bukkit.getWorld(data[0]), Double.valueOf(data[1]), Double.valueOf(data[2]), Double.valueOf(data[3]));

        return null;
    }

    public static String strFromLocation(Location location) {
        return Objects.requireNonNull(location.getWorld()).toString() + "," + location.getX() + "," + location.getY() + "," + location.getZ();
    }
}

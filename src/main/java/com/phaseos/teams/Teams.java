package com.phaseos.teams;

import com.phaseos.team.Team;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public final class Teams extends JavaPlugin {

    public Set<Team> onlineTeams = new HashSet<>();

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

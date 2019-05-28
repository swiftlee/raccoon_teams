package com.phaseos.teams;

import com.phaseos.cmds.TeamCommand;
import com.phaseos.command.Commands;
import com.phaseos.team.Team;
import com.phaseos.util.StringUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public final class Teams extends JavaPlugin {

    public Set<Team> onlineTeams = new HashSet<>();
    private Commands commands;

    @Override
    public void onEnable() {
        // load YML configurations
        Team.TeamDatabase db = new Team.TeamDatabase();
        db.load();

        // command registry
        commands = new Commands(this).setErrorMessages(StringUtils.fmt("&cThat player does not exist!"),
                StringUtils.fmt("&cThat's not a number"),
                StringUtils.fmt("&cYou don't have any permissions to this.")).registerCommand(new TeamCommand(this)).registerCommand(new TeamCommand.SpawnCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

package com.phaseos.teams;

import com.phaseos.cmds.TeamCommand;
import com.phaseos.command.Commands;
import com.phaseos.team.Team;
import com.phaseos.util.StringUtils;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class Teams extends JavaPlugin {

    private static Set<Team> onlineTeams = new HashSet<>();
    private static HashMap<UUID, Scoreboard> onlineBoards;
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

    public void addBoard(UUID uuid, Scoreboard board) {
        onlineBoards.put(uuid, board);
    }

    public void removeBoard(UUID uuid) {
        onlineBoards.remove(uuid);
    }

    public Set<Team> getOnlineTeams() {
        return onlineTeams;
    }
}

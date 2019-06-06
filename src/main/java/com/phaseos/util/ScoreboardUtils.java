package com.phaseos.util;

import com.phaseos.team.TeamMember;
import com.phaseos.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardUtils {

    private Teams plugin;

    public ScoreboardUtils(Teams plugin) {
        this.plugin = plugin;
    }

    public void constructBoard(TeamMember teamMember) {
        // team: example
        // warp: time (if not 0)
        // spawn protection: yes/no
        String boardName = StringUtils.fmt(plugin.getConfig().getString("scoreboard-name"));
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = board.registerNewObjective("test","dummy", boardName);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score team;
        Score warp;
        Score spawnProtection;

        plugin.addBoard(teamMember.getTeamMemberId(), board);
    }

}

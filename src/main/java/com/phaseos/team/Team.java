package com.phaseos.team;

import com.phaseos.util.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import org.bukkit.Location;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

public class Team {

    public static YamlConfiguration teamData = new YamlConfiguration();
    private final UUID teamId;
    private String name;
    private boolean isPublic;
    private UUID leader;
    private boolean friendlyFire;
    private Location hq;
    private Location warp;
    private Location rally;
    private boolean anyOnline;

    public Team(UUID teamId) {
        this.teamId = teamId;
    }

    // use this for creating a new team
    public Team(String name, UUID leader) {
        this.teamId = UUID.randomUUID();
        create(name, leader, this.teamId);
    }

    private Team create(String name, UUID leader, UUID teamId) {

        if (name.length() > 16) {
            Bukkit.getPlayer(leader).sendMessage(StringUtils.fmt("&cTeam name must contain 16 characters or less."));
            return null;
        }

        this.name = name;
        teamData.set(teamId.toString() + ".name", name);
        this.isPublic = false;
        teamData.set(teamId.toString() + ".isPublic", false);
        this.friendlyFire = false;
        teamData.set(teamId.toString() + ".friendlyFire", false);
        this.anyOnline = true;
        teamData.set(teamId.toString() + ".anyOnline", true);

        return this;
    }

    public static Team loadTeam(UUID teamId) {
        return null;
    }

    public static void removeTeam(UUID teamId) {

    }

    public UUID getTeamId() {
        return teamId;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public UUID getLeader() {
        return leader;
    }

    public void setLeader(UUID leader) {
        this.leader = leader;
    }

    public boolean isFriendlyFire() {
        return friendlyFire;
    }

    public void setFriendlyFire(boolean friendlyFire) {
        this.friendlyFire = friendlyFire;
    }

    public Location getHq() {
        return hq;
    }

    public void setHq(Location hq) {
        this.hq = hq;
    }

    public Location getWarp() {
        return warp;
    }

    public void setWarp(Location warp) {
        this.warp = warp;
    }

    public Location getRally() {
        return rally;
    }

    public void setRally(Location rally) {
        this.rally = rally;
    }

    public boolean isAnyOnline() {
        return anyOnline;
    }

    public void setAnyOnline(boolean anyOnline) {
        this.anyOnline = anyOnline;
    }

    public static class TeamDatabase {

        public static YamlConfiguration getYml() {
            return teamData;
        }

        public void save() {

            try {
                teamData.save("plugins/teams/teams.yml");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        private boolean exists() {

            File f = new File("plugins/teams/teams.yml");

            return f.exists() && !f.isDirectory();

        }

        public void load() {

            if (teamData == null) {
                teamData = new YamlConfiguration();
            }

            try {
                teamData.load("plugins/teams/teams.yml");
            } catch (FileNotFoundException e1) {
                save();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}

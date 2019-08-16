package com.phaseos.team;

import com.phaseos.util.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import org.bukkit.Location;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Team {

    public static YamlConfiguration teamData = new YamlConfiguration();
    private final UUID teamId;
    private String name;
    private boolean isPublic;
    private UUID leader;
    private Set<UUID> captains;
    private boolean friendlyFire;
    private Location hq;
    private Location warp;
    private Location rally;
    private boolean anyOnline;
    private Set<UUID> teamMembers;
    private static TeamDatabase db = new TeamDatabase();


    public Team(UUID teamId) {
        this.teamId = teamId;
        fillData();
    }

    /**
     * This constructor is only called when a previous exists check is fired.
     *
     * @param teamName the team name to gather data for.
     */
    public Team(String teamName) {
        this(getTeamId(teamName));
    }

    /**
     * Only use this for new team creation.
     *
     * @param name
     * @param leader
     */
    public Team(String name, UUID leader) {
        this.teamId = UUID.randomUUID();
        create(name, leader);
    }

    public static UUID getTeamId(String teamName) {
        for (String teamId : teamData.getKeys(false)) {
            String name = teamData.getString(teamId + ".name");
            if (name != null && name.equalsIgnoreCase(teamName))
                return UUID.fromString(teamId);
        }
        return null;
    }

    public static boolean exists(String teamName) {
        for (String key : teamData.getKeys(false)) {
            String name = teamData.getString(key + ".name");
            if (name != null && name.equalsIgnoreCase(teamName))
                return true;
        }
        return false;
    }

    private void create(String name, UUID leader) {
        if (name.length() > 16)
            Bukkit.getPlayer(leader).sendMessage(StringUtils.fmt("&cTeam name must contain 16 characters or less."));
        else {
            this.name = name;
            this.setAttribute("name", name);
            setPublic(false);
            setFriendlyFire(false);
            setAnyOnline(true);
            setLeader(leader);
            this.captains = new HashSet<>();
            this.setAttribute("captains", new ArrayList<>());
            this.teamMembers = new HashSet<>();
            addTeamMember(leader);
            this.hq = null;
            this.setAttribute("hq", "NULL");
            this.warp = null;
            this.setAttribute("warp", "NULL");
            this.rally = null;
            this.setAttribute("rally", "NULL");
            Bukkit.getPlayer(leader).sendMessage(StringUtils.fmt(""));
        }
    }

    private void fillData() {
        this.name = teamData.getString(teamId.toString() + ".name");
        this.isPublic = teamData.getBoolean(teamId.toString() + ".isPublic");
        this.friendlyFire = teamData.getBoolean(teamId.toString() + ".friendlyFire");
        this.anyOnline = teamData.getBoolean(teamId.toString() + ".anyOnline");
        this.leader = UUID.fromString(Objects.requireNonNull(teamData.getString(teamId.toString() + ".leader")));
        this.captains = teamData.getStringList(teamId.toString() + ".captains").stream().map(UUID::fromString).collect(Collectors.toSet());
        this.teamMembers = teamData.getStringList(teamId.toString() + ".teamMembers").stream().map(UUID::fromString).collect(Collectors.toSet());
        this.hq = StringUtils.locationFromStr(Objects.requireNonNull(teamData.getString(teamId.toString() + ".hq")));
        this.warp = StringUtils.locationFromStr(Objects.requireNonNull(teamData.getString(teamId.toString() + ".warp")));
        this.rally = StringUtils.locationFromStr(Objects.requireNonNull(teamData.getString(teamId.toString() + ".rally")));
    }

    public void addCaptain(UUID captain) {
        captains.add(captain);
        this.setAttribute("captains", captains.stream().map(UUID::toString).collect(Collectors.toList()));
    }

    public void removeCaptain(UUID captain) {
        captains.remove(captain);
        this.setAttribute("captains", captains.stream().map(UUID::toString).collect(Collectors.toList()));
    }

    public Set<UUID> getCaptains() {
        return captains;
    }

    public void addTeamMember(UUID teamMember) {
        teamMembers.add(teamMember);
        this.setAttribute("teamMembers", teamMembers.stream().map(UUID::toString).collect(Collectors.toList()));
    }

    public void removeTeamMember(UUID teamMember) {
        teamMembers.remove(teamMember);
        this.setAttribute("teamMembers", teamMembers.stream().map(UUID::toString).collect(Collectors.toList()));
    }

    public static void removeTeam(UUID teamId) {
        teamData.set(teamId.toString(), null);
        reloadTeamData();
    }

    public UUID getTeamId() {
        return teamId;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean pub) {
        isPublic = pub;
        setAttribute("isPublic", pub);
    }

    public UUID getLeader() {
        return leader;
    }

    public void setLeader(UUID leader) {
        this.leader = leader;
        setAttribute("leader", leader);
    }

    public boolean isFriendlyFire() {
        return friendlyFire;
    }

    public void setFriendlyFire(boolean friendlyFire) {
        this.friendlyFire = friendlyFire;
        setAttribute("friendlyFire", friendlyFire);
    }

    public Location getHq() {
        return hq;
    }

    public void setHq(Location hq) {
        this.hq = hq;
        setAttribute("hq", StringUtils.strFromLocation(hq));
    }

    public Location getWarp() {
        return warp;
    }

    public void setWarp(Location warp) {
        this.warp = warp;
        setAttribute("warp", StringUtils.strFromLocation(warp));
    }

    public Location getRally() {
        return rally;
    }

    public void setRally(Location rally) {
        this.rally = rally;
        setAttribute("rally", StringUtils.strFromLocation(rally));
    }

    public boolean anyOnline() {
        return anyOnline;
    }

    public void setAnyOnline(boolean anyOnline) {
        this.anyOnline = anyOnline;
        setAttribute("anyOnline", anyOnline);
    }

    public Set<UUID> getTeamMembers() {
        return teamMembers;
    }

    public String getName() {
        return name;
    }

    /**
     * Sets a value in the configuration file and reloads the file.
     *
     * @param node  the node at which you want to set a value
     * @param value the value to set at the selected node
     */
    private void setAttribute(String node, boolean value) {
        teamData.set(teamId.toString() + "." + node, value);
        reloadTeamData();
    }

    private void setAttribute(String node, String value) {
        teamData.set(teamId.toString() + "." + node, value);
        reloadTeamData();
    }

    private void setAttribute(String node, int value) {
        teamData.set(teamId.toString() + "." + node, value);
        reloadTeamData();
    }

    private void setAttribute(String node, UUID value) {
        setAttribute(node, value.toString());
    }

    private void setAttribute(String node, List<String> value) {
        teamData.set(teamId.toString() + "." + node, value);
        reloadTeamData();
    }

    private static void reloadTeamData() {
        db.save();
        db.load();
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

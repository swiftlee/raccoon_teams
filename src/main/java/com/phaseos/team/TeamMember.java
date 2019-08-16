package com.phaseos.team;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.UUID;

public class TeamMember {

    public static YamlConfiguration teamMemberData = new YamlConfiguration();
    private static TeamMemberDatabase db = new TeamMemberDatabase();
    private UUID teamMemberId;
    private Team team;
    private Rank rank;
    private Location targetedWarp;
    private boolean isTeamChatToggled = false;
    HashSet<UUID> teamInvitations = new HashSet<>();

    public TeamMember(UUID teamMemberId) {
        this.teamMemberId = teamMemberId;
        fillData();
    }

    private static void reloadMemberData() {
        db.save();
        db.load();
    }

    private void fillData() {
        String teamId = teamMemberData.getString(teamMemberId.toString() + ConfigNode.Team_ID.getNode());
        this.team = teamId != null && !teamId.equalsIgnoreCase("NULL") ? new Team(UUID.fromString(teamId)) : null;
        String rank = teamMemberData.getString(teamMemberId.toString() + ConfigNode.RANK.getNode());
        this.rank = rank != null && !rank.equalsIgnoreCase("NULL") ? Rank.fromString(rank) : null;
        this.isTeamChatToggled = teamMemberData.getBoolean(teamMemberId.toString() + ConfigNode.TEAM_CHAT.getNode());
    }

    public boolean hasTeam() {
        return team != null;
    }

    public boolean isLeader() {
        return hasTeam() && team.getLeader().equals(teamMemberId);
    }

    public void join(String teamName) {
        if (Team.exists(teamName)) {
            Team team = new Team(teamName);
            team.addTeamMember(teamMemberId);
        }
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public UUID getTeamMemberId() {
        return teamMemberId;
    }

    public boolean isTeamChatToggled() {
        return isTeamChatToggled;
    }

    public void setTeamChatToggled(boolean teamChatToggled) {
        setAttribute(ConfigNode.TEAM_CHAT, teamChatToggled);
    }

    private void setAttribute(ConfigNode node, boolean value) {
        teamMemberData.set(teamMemberId.toString() + node.getNode(), value);
        reloadMemberData();
    }

    private enum ConfigNode {
        Team_ID(".team"), RANK(".rank"), TEAM_CHAT(".team-chat");

        private String node;

        ConfigNode(String node) {
            this.node = node;
        }

        public String getNode() {
            return node;
        }
    }

    enum Permission {
        TEAMS_ADMIN, TEAMS_USE;

        @Override
        public String toString() {
            if (this == TEAMS_ADMIN) return "teams.admin";
            else if (this == TEAMS_USE) return "teams.use";
            return "";
        }
    }

    public static class TeamMemberDatabase {

        public static YamlConfiguration getYml() {
            return teamMemberData;
        }

        public void save() {
            try {
                teamMemberData.save("plugins/teams/teamMembers.yml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private boolean exists() {
            File f = new File("plugins/teams/teamMembers.yml");
            return f.exists() && !f.isDirectory();
        }

        public void load() {
            if (teamMemberData == null) {
                teamMemberData = new YamlConfiguration();
            }

            try {
                teamMemberData.load("plugins/teams/teamMembers.yml");
            } catch (FileNotFoundException e1) {
                save();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}

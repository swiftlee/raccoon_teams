package com.phaseos.team;

import com.phaseos.util.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

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
        join(Team.getTeamId(teamName));
    }

    public void join(UUID teamId) {
        if (Team.exists(teamId)) {
            Team team = new Team(teamId);
            team.addTeamMember(teamMemberId);
            setTeam(teamId);
            Player newMember = Bukkit.getPlayer(teamMemberId);
            newMember.sendMessage("&3You successfully joined " + team.getName() + "!");
            for (UUID member : team.getTeamMembers()) {
                Player p = Bukkit.getPlayer(member);
                if (p != null && p.isOnline())
                    p.sendMessage(StringUtils.fmt("&f" + newMember.getName() + " &3has joined the team!"));
            }
        }
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
        this.setAttribute(ConfigNode.RANK, rank.toString());
    }

    public void setTeam(UUID teamId) {
        this.team = new Team(teamId);
        this.setAttribute(ConfigNode.Team_ID, teamId.toString());
    }

    public UUID getTeamMemberId() {
        return teamMemberId;
    }

    public boolean isTeamChatToggled() {
        return isTeamChatToggled;
    }

    public void setTeamChatToggled(boolean teamChatToggled) {
        this.isTeamChatToggled = teamChatToggled;
        setAttribute(ConfigNode.TEAM_CHAT, teamChatToggled);
    }

    private void setAttribute(ConfigNode node, Object value) {
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

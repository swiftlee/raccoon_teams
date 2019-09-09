package com.phaseos.cmds;

import com.phaseos.command.ArgumentParser;
import com.phaseos.command.Command;
import com.phaseos.command.PlayerCommand;
import com.phaseos.team.Team;
import com.phaseos.team.TeamMember;
import com.phaseos.teams.Teams;
import com.phaseos.util.StringUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class TeamCommand extends Command {
    private static final String commandsHeader = StringUtils.fmt("&8&m----------------&r&8[ &6Gang Commands &8]&m----------------");
    private static final String listHeader = StringUtils.fmt("&8&m----------------&r&8[ &6Gangs List &8]&m----------------");
    private static final String topHeader = StringUtils.fmt("&8&m----------------&r&8[ &6Top Gangs &8]&m----------------");
    private static final String infoHeader = StringUtils.fmt("&8&m----------------&r&8[ &6%gang% &8]&m----------------");
    private Teams plugin;

    public TeamCommand(Teams plugin) {
        super("team", "t");
        this.plugin = plugin;
        addSubCommand(new HelpCommand());
        addSubCommand(new CreateCommand());
        addSubCommand(new JoinCommand());
        addSubCommand(new SetPublicCommand());
        addSubCommand(new DisbandCommand());
        addSubCommand(new SetLeaderCommand());
        addSubCommand(new PromoteCommand());
        addSubCommand(new DemoteCommand());
        addSubCommand(new ToggleFriendlyFireCommand());
        addSubCommand(new KickCommand());
        addSubCommand(new InviteCommand());
        addSubCommand(new SetHqCommand());
        addSubCommand(new SetWarpCommand());
        addSubCommand(new DelWarpCommand());
        addSubCommand(new SetRallyCommand());
        addSubCommand(new HqCommand());
        addSubCommand(new RallyCommand());
        addSubCommand(new WarpCommand());
        addSubCommand(new LeaveCommand());
        addSubCommand(new TeamChatCommand());
        addSubCommand(new InfoCommand());
    }

    private static boolean areNearbyPlayers(Player player) {
        for (Entity e : player.getNearbyEntities(20, 20, 20)) {
            if (e instanceof Player)
                return true;
        }
        return false;
    }

    private static int warp(Player player, Location location, Teams plugin) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                if (Teams.ongoingWarps.containsKey(player.getUniqueId()) && Teams.ongoingWarps.get(player.getUniqueId()) == this.getTaskId()) {
                    player.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
                    // remove player from map post-teleport
                    Teams.ongoingWarps.remove(player.getUniqueId());
                }
            }
        }.runTaskLater(plugin, 20 * 10L).getTaskId();
    }

    @Override
    protected void execute(CommandSender sender, ArgumentParser ap) {
        if (sender instanceof Player) {

            Player p = (Player) sender;

        } else
            sender.sendMessage(StringUtils.fmt("&cYou cannot execute this command."));
    }

    public static class SpawnCommand extends Command {
        private Teams plugin;

        public SpawnCommand(Teams plugin) {
            super("spawn");
            this.plugin = plugin;
        }

        @Override
        protected void execute(CommandSender sender, ArgumentParser ap) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (areNearbyPlayers(player))
                    player.sendMessage(StringUtils.fmt("&4Teleport cancelled!"));
                else {
                    // player currently is attempting to warp elsewhere
                    if (Teams.ongoingWarps.containsKey(player.getUniqueId())) {
                        // player canceled their old warp, reset so they can travel to new warp
                        Bukkit.getServer().getScheduler().cancelTask(Teams.ongoingWarps.get(player.getUniqueId()));
                        player.sendMessage(StringUtils.fmt("&4Cancelled your previous teleport!"));
                    }
                    //noinspection ConstantConditions
                    Teams.ongoingWarps.put(player.getUniqueId(), warp(player, Objects.requireNonNull(StringUtils.locationFromStr(plugin.getConfig().getString("spawn-location"))), plugin));
                }
            }
        }
    }

    public static class TeamChatCommand extends Command {


        public TeamChatCommand() {
            super("teamchat");
        }

        @Override
        protected void execute(CommandSender sender, ArgumentParser ap) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                TeamMember member = new TeamMember(player.getUniqueId());
                if (member.hasTeam()) {
                    // invert teamchat toggle
                    member.setTeamChatToggled(!member.isTeamChatToggled());
                    // display message on teamchat toggle
                    if (member.isTeamChatToggled())
                        player.sendMessage(StringUtils.fmt("&3Teamchat is now &aON&3."));
                    else
                        player.sendMessage(StringUtils.fmt("&3Teamchat is now &cOFF&3."));
                } else
                    player.sendMessage(StringUtils.fmt("&cYou must be in a team use this command."));
            } else
                sender.sendMessage(StringUtils.fmt("&cYou must be a player to execute this command!"));
        }

    }

    private class HelpCommand extends PlayerCommand {

        public HelpCommand() {
            super("help");
        }

        @Override
        protected void execute(Player player, ArgumentParser ap) {
            player.sendMessage(commandsHeader);
            for (String subCommandName : getParent().getSubCommandNames())
                player.sendMessage("§e/g " + subCommandName);
        }

    }

    private class CreateCommand extends PlayerCommand {
        public CreateCommand() {
            super("create");
        }

        @Override
        protected void execute(Player player, ArgumentParser ap) {
            TeamMember member = new TeamMember(player.getUniqueId());
            if (!member.hasTeam())
                new Team(ap.get(1), member.getTeamMemberId());
            else
                player.sendMessage(StringUtils.fmt("&cYou are already in a team!"));

        }
    }

    private class JoinCommand extends PlayerCommand {
        public JoinCommand() {
            super("join");
        }

        @Override
        protected void execute(Player player, ArgumentParser ap) {
            TeamMember member = new TeamMember(player.getUniqueId());
            if (!member.hasTeam()) {
                if (ap.hasExactly(1)) {
                } else {
                    // join first team in queue
                    member.join(Team.getTeamId(ap.get(0)));

                }
            } else
                player.sendMessage(StringUtils.fmt("&cYou are already in a team!"));
        }
    }

    private class SetPublicCommand extends PlayerCommand {

        public SetPublicCommand() {
            super("setpublic");
        }

        @Override
        protected void execute(Player player, ArgumentParser ap) {


        }

    }

    private class DisbandCommand extends PlayerCommand {

        public DisbandCommand() {
            super("disband");
        }

        @Override
        protected void execute(Player player, ArgumentParser ap) {


        }

    }

    private class SetLeaderCommand extends PlayerCommand {

        public SetLeaderCommand() {
            super("setleader");
        }

        @Override
        protected void execute(Player player, ArgumentParser ap) {


        }

    }

    private class PromoteCommand extends PlayerCommand {

        public PromoteCommand() {
            super("promote");
        }

        @Override
        protected void execute(Player player, ArgumentParser ap) {


        }

    }

    private class DemoteCommand extends PlayerCommand {

        public DemoteCommand() {
            super("demote");
        }

        @Override
        protected void execute(Player player, ArgumentParser ap) {


        }

    }

    private class ToggleFriendlyFireCommand extends PlayerCommand {

        public ToggleFriendlyFireCommand() {
            super("togglefriendlyfire");
        }

        @Override
        protected void execute(Player player, ArgumentParser ap) {


        }

    }

    private class KickCommand extends PlayerCommand {

        public KickCommand() {
            super("kick");
        }

        @Override
        protected void execute(Player player, ArgumentParser ap) {


        }

    }

    private class InviteCommand extends PlayerCommand {

        public InviteCommand() {
            super("invite");
        }

        @Override
        protected void execute(Player player, ArgumentParser ap) {


        }

    }

    private class SetHqCommand extends PlayerCommand {

        public SetHqCommand() {
            super("sethq");
        }

        @Override
        protected void execute(Player player, ArgumentParser ap) {


        }

    }

    private class SetWarpCommand extends PlayerCommand {

        public SetWarpCommand() {
            super("warp");
        }

        @Override
        protected void execute(Player player, ArgumentParser ap) {


        }

    }

    private class DelWarpCommand extends PlayerCommand {

        public DelWarpCommand() {
            super("delwarp");
        }

        @Override
        protected void execute(Player player, ArgumentParser ap) {


        }

    }

    private class SetRallyCommand extends PlayerCommand {

        public SetRallyCommand() {
            super("setrally");
        }

        @Override
        protected void execute(Player player, ArgumentParser ap) {


        }

    }

    private class HqCommand extends PlayerCommand {

        public HqCommand() {
            super("hq");
        }

        @Override
        protected void execute(Player player, ArgumentParser ap) {


        }

    }

    private class RallyCommand extends PlayerCommand {

        public RallyCommand() {
            super("rally");
        }

        @Override
        protected void execute(Player player, ArgumentParser ap) {


        }

    }

    private class WarpCommand extends PlayerCommand {

        public WarpCommand() {
            super("warp");
        }

        @Override
        protected void execute(Player player, ArgumentParser ap) {


        }

    }

    private class LeaveCommand extends PlayerCommand {

        public LeaveCommand() {
            super("leave");
        }

        @Override
        protected void execute(Player player, ArgumentParser ap) {


        }

    }

    private class InfoCommand extends PlayerCommand {

        public InfoCommand() {
            super("info");
        }

        @Override
        protected void execute(Player player, ArgumentParser ap) {


        }

    }

}

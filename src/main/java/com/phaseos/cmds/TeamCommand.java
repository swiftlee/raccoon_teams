package com.phaseos.cmds;

import com.phaseos.command.ArgumentParser;
import com.phaseos.command.Command;
import com.phaseos.command.PlayerCommand;
import com.phaseos.teams.Teams;
import com.phaseos.util.StringUtils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

    }

    public static class SpawnCommand extends Command {

        public SpawnCommand() {
            super("spawn");
        }

        @Override
        protected void execute(CommandSender sender, ArgumentParser ap) {

        }
    }

    @Override
    protected void execute(CommandSender sender, ArgumentParser ap) {
        if (sender instanceof Player) {

            Player p = (Player) sender;

        } else
            sender.sendMessage(StringUtils.fmt("&cYou cannot execute this command."));
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


        }

    }

    private class JoinCommand extends PlayerCommand {


        public JoinCommand() {
            super("join");
        }

        @Override
        protected void execute(Player player, ArgumentParser ap) {


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

    private class TeamChatCommand extends PlayerCommand {

        public TeamChatCommand() {
            super("teamchat");
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

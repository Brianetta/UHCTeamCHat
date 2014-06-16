package com.elllzman.java.TeamChat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;



public class TeamChat extends JavaPlugin implements Listener {


    private ScoreboardManager manager;
    private Scoreboard board;



    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
        manager = Bukkit.getScoreboardManager();
        board = manager.getMainScoreboard();
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(cmd.getName().equalsIgnoreCase("pm"))
        {
            Player p = (Player)sender;

            if(args.length == 0) {
                sender.sendMessage(ChatColor.RED + "You need to enter a message");
                return true;
            }

            Team team = this.board.getPlayerTeam(p);

            if(null == team) {
                p.sendMessage(ChatColor.RED + "You are not on a team");
                return true;
            }

            String prefix = this.board.getPlayerTeam(p).getPrefix();

            StringBuilder sb = new StringBuilder();

            sb.append("§6[§l§fPM§r§6]§f ").append(prefix).append(p.getName()).append(":§f ").append(ChatColor.ITALIC);
            for (String arg : args) {
                sb.append(" ").append(arg);
            }

            String message = sb.toString();

            for(OfflinePlayer offlineplayer: this.board.getPlayerTeam(p).getPlayers()) {
                if(offlineplayer.isOnline()) {
                    Player player = (Player) offlineplayer;
                    player.sendMessage(message);
                }
            }


            return true;
        }


        if(cmd.getName().equalsIgnoreCase("sendcoords"))
        {
            Player p = (Player)sender;
            String prefix = this.board.getPlayerTeam(p).getPrefix();

            int X = (int)p.getLocation().getX();
            int Y = (int)p.getLocation().getY();
            int Z = (int)p.getLocation().getZ();
            Team team = this.board.getPlayerTeam(p);
            for (Player player : Bukkit.getServer().getOnlinePlayers())
            {
                if(this.board.getPlayerTeam(player) == team)
                {
                    player.sendMessage("§6[§l§fPM§r§6]§f" + " " + prefix + p.getName() + ":§f " + ChatColor.ITALIC + " I am at X: " + X + " Y: " + Y + " Z: " + Z);
                }
            }
            return true;
        }

        if(cmd.getName().equalsIgnoreCase("getcoords"))
        {
            Player p = (Player)sender;
            Player p2 = getServer().getPlayer(args[0].toString());

            String prefix = this.board.getPlayerTeam(p).getPrefix();

            int X = (int)p2.getLocation().getX();
            int Y = (int)p2.getLocation().getY();
            int Z = (int)p2.getLocation().getZ();

            if(this.board.getPlayerTeam(p)==this.board.getPlayerTeam(p2))
            {
                sender.sendMessage(ChatColor.RED + "§6[§l§fPM§r§6]§f" + prefix + p2.getName() + ":§f " + ChatColor.ITALIC + "I am at X: " + X + " Y: " + Y + " Z: " + Z);
            }
            else if(this.board.getPlayerTeam(p)!=this.board.getPlayerTeam(p2))
            {
                sender.sendMessage(ChatColor.RED + "That player is not oon your team");
                return true;
            }


        }
        return true;

    }

}
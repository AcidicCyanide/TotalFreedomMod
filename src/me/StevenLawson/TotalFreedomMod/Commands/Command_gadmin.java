package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.Commands.CommandPermissions.ADMIN_LEVEL;
import me.StevenLawson.TotalFreedomMod.Commands.CommandPermissions.SOURCE_TYPE_ALLOWED;
import me.StevenLawson.TotalFreedomMod.TFM_ServerInterface;
import me.StevenLawson.TotalFreedomMod.TFM_PlayerData;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = ADMIN_LEVEL.SUPER, source = SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions = false)
public class Command_gadmin extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 0)
        {
            return false;
        }

        String mode = args[0].toLowerCase();

        if (mode.equals("list"))
        {
            playerMsg("[ Real Name ] : [ Display Name ] - Hash:");
        }

        for (Player p : server.getOnlinePlayers())
        {
            String hash = p.getUniqueId().toString().substring(0, 4);
            if (mode.equals("list"))
            {
                sender.sendMessage(ChatColor.GRAY + String.format("[ %s ] : [ %s ] - %s",
                        p.getName(),
                        ChatColor.stripColor(p.getDisplayName()),
                        hash));
            }
            else if (hash.equalsIgnoreCase(args[1]))
            {
                if (mode.equals("kick"))
                {
                    TFM_Util.adminAction(sender.getName(), String.format("Kicking: %s.", p.getName()), false);
                    p.kickPlayer("Kicked by Administrator");
                }
                else if (mode.equals("nameban"))
                {
                    TFM_ServerInterface.banUsername(p.getName(), null, null, null);
                    TFM_Util.adminAction(sender.getName(), String.format("Banning Name: %s.", p.getName()), true);
                    p.kickPlayer("Username banned by Administrator.");
                }
                else if (mode.equals("ipban"))
                {
                    String user_ip = p.getAddress().getAddress().getHostAddress();
                    String[] ip_parts = user_ip.split("\\.");
                    if (ip_parts.length == 4)
                    {
                        user_ip = String.format("%s.%s.*.*", ip_parts[0], ip_parts[1]);
                    }
                    TFM_Util.adminAction(sender.getName(), String.format("Banning IP: %s.", p.getName(), user_ip), true);
                    TFM_ServerInterface.banIP(user_ip, null, null, null);
                    p.kickPlayer("IP address banned by Administrator.");
                }
                else if (mode.equals("ban"))
                {
                    String user_ip = p.getAddress().getAddress().getHostAddress();
                    String[] ip_parts = user_ip.split("\\.");
                    if (ip_parts.length == 4)
                    {
                        user_ip = String.format("%s.%s.*.*", ip_parts[0], ip_parts[1]);
                    }
                    TFM_Util.adminAction(sender.getName(), String.format("Banning Name: %s, IP: %s.", p.getName(), user_ip), true);
                    TFM_ServerInterface.banIP(user_ip, null, null, null);
                    TFM_ServerInterface.banUsername(p.getName(), null, null, null);
                    p.kickPlayer("IP and username banned by Administrator.");
                }
                else if (mode.equals("op"))
                {
                    TFM_Util.adminAction(sender.getName(), String.format("Opping %s.", p.getName()), false);
                    p.setOp(false);
                    p.sendMessage(TotalFreedomMod.YOU_ARE_OP);
                }
                else if (mode.equals("deop"))
                {
                    TFM_Util.adminAction(sender.getName(), String.format("Deopping %s.", p.getName()), false);
                    p.setOp(false);
                    p.sendMessage(TotalFreedomMod.YOU_ARE_NOT_OP);
                }
                else if (mode.equals("ci"))
                {
                    p.getInventory().clear();
                }
                else if (mode.equals("fr"))
                {
                    TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(p);
                    playerdata.setFrozen(!playerdata.isFrozen());

                    playerMsg(p.getName() + " has been " + (playerdata.isFrozen() ? "frozen" : "unfrozen") + ".");
                    p.sendMessage(ChatColor.AQUA + "You have been " + (playerdata.isFrozen() ? "frozen" : "unfrozen") + ".");
                }

                return true;
            }
        }

        if (!mode.equals("list"))
        {
            playerMsg("Invalid hash.", ChatColor.RED);
        }

        return true;
    }
}

package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.Commands.CommandPermissions.ADMIN_LEVEL;
import me.StevenLawson.TotalFreedomMod.Commands.CommandPermissions.SOURCE_TYPE_ALLOWED;
import me.StevenLawson.TotalFreedomMod.TFM_PlayerData;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = ADMIN_LEVEL.SUPER, source = SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions = false)
public class Command_lastcmd extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 0)
        {
            return false;
        }

        Player p;
        try
        {
            p = getPlayer(args[0]);
        }
        catch (CantFindPlayerException ex)
        {
            playerMsg(ex.getMessage(), ChatColor.RED);
            return true;
        }

        TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(p);

        if (playerdata != null)
        {
            String last_command = playerdata.getLastCommand();
            if (last_command.isEmpty())
            {
                last_command = "(none)";
            }
            playerMsg(p.getName() + " - Last Command: " + last_command, ChatColor.GRAY);
        }

        return true;
    }
}

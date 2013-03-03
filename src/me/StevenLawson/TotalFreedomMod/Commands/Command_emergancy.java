package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.Commands.CommandPermissions.ADMIN_LEVEL;
import me.StevenLawson.TotalFreedomMod.Commands.CommandPermissions.SOURCE_TYPE_ALLOWED;
import me.StevenLawson.TotalFreedomMod.TFM_SuperadminList;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = ADMIN_LEVEL.SENIOR, source = SOURCE_TYPE_ALLOWED.ONLY_IN_GAME, block_host_console = true, ignore_permissions = false)
public class Command_adminmode extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args[0].equalsIgnoreCase("off"))
        {
            TotalFreedomMod.adminOnlyMode = false;
            TFM_Util.adminAction(sender.getName(), "Opening the server to all players.", true);
            return true;
        }
        else if (args[0].equalsIgnoreCase("on"))
        {
            TotalFreedomMod.adminOnlyMode = true;
            TFM_Util.adminAction(sender.getName(), "Closing the server to non-superadmins.", true);
            for (Player p : server.getOnlinePlayers())
            {
                if (!TFM_SuperadminList.isUserSuperadmin(p))
                {
                    p.kickPlayer("Server is now closed to non-superadmins.");
                }
            }
            return true;
        }

        return false;
    }
}

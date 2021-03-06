package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.Commands.CommandPermissions.ADMIN_LEVEL;
import me.StevenLawson.TotalFreedomMod.Commands.CommandPermissions.SOURCE_TYPE_ALLOWED;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = ADMIN_LEVEL.SENIOR, source = SOURCE_TYPE_ALLOWED.ONLY_CONSOLE, block_host_console = true, ignore_permissions = false)
public class Command_wipeflatlands extends TFM_Command
{
    @Override
    public boolean run(final CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        TFM_Util.setSavedFlag("do_wipe_flatlands", true);

        TFM_Util.bcastMsg("Server is going offline for flatlands wipe.", ChatColor.GRAY);

        for (Player p : server.getOnlinePlayers())
        {
            p.kickPlayer("Server is going offline for flatlands wipe, come back in a few minutes.");
        }

        server.shutdown();

        return true;
    }
}

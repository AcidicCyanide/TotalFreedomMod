package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.Commands.CommandPermissions.ADMIN_LEVEL;
import me.StevenLawson.TotalFreedomMod.Commands.CommandPermissions.SOURCE_TYPE_ALLOWED;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = ADMIN_LEVEL.SENIOR, source = SOURCE_TYPE_ALLOWED.ONLY_IN_GAME, ignore_permissions = false)
public class Command_fuckoff extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length < 1)
        {
            return false;
        }

        boolean fuckoff_enabled = false;
        double fuckoff_range = 25.0;

        if (args[0].equalsIgnoreCase("on"))
        {
            fuckoff_enabled = true;

            if (args.length >= 2)
            {
                try
                {
                    fuckoff_range = Math.max(5.0, Math.min(100.0, Double.parseDouble(args[1])));
                }
                catch (NumberFormatException ex)
                {
                }
            }
        }

        if (TotalFreedomMod.fuckoffEnabledFor.containsKey(sender_p))
        {
            TotalFreedomMod.fuckoffEnabledFor.remove(sender_p);
        }

        if (fuckoff_enabled)
        {
            TotalFreedomMod.fuckoffEnabledFor.put(sender_p, new Double(fuckoff_range));
        }

        playerMsg("Fuckoff " + (fuckoff_enabled ? ("enabled. Range: " + fuckoff_range + ".") : "disabled."));

        return true;
    }
}

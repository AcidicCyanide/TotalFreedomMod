package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.Commands.CommandPermissions.ADMIN_LEVEL;
import me.StevenLawson.TotalFreedomMod.Commands.CommandPermissions.SOURCE_TYPE_ALLOWED;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

@CommandPermissions(level = ADMIN_LEVEL.OP, source = SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions = false)
public class Command_mp extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        sender.sendMessage(ChatColor.GRAY + "Purging all mobs...");

        int removed = 0;
        for (World world : server.getWorlds())
        {
            for (Entity ent : world.getLivingEntities())
            {
                if (ent instanceof Creature || ent instanceof Ghast || ent instanceof Slime || ent instanceof EnderDragon || ent instanceof Ambient)
                {
                    ent.remove();
                    removed++;
                }
            }
        }

        playerMsg(removed + " mobs removed.");

        return true;
    }
}

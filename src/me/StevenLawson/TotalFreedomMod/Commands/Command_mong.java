package me.StevenLawson.TotalFreedomMod.Commands;

import java.text.SimpleDateFormat;
import java.util.Date;
import me.StevenLawson.TotalFreedomMod.Commands.CommandPermissions.ADMIN_LEVEL;
import me.StevenLawson.TotalFreedomMod.Commands.CommandPermissions.SOURCE_TYPE_ALLOWED;
import me.StevenLawson.TotalFreedomMod.TFM_ServerInterface;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = ADMIN_LEVEL.SUPER, source = SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions = false)
public class Command_mong extends TFM_Command
{
    private static final SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd \'at\' HH:mm:ss z");

    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length < 1)
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

        StringBuilder bcast_msg = new StringBuilder("Temporarily banned " + p.getName());

        Date ban_duration = TFM_Util.parseDateOffset("10m");
        if (args.length >= 2)
        {
            Date parsed_offset = TFM_Util.parseDateOffset(args[1]);
            if (parsed_offset != null)
            {
                ban_duration = parsed_offset;
            }
        }
        bcast_msg.append(" until ").append(date_format.format(ban_duration));

        String ban_reason = "Banned by " + sender.getName();
        if (args.length >= 3)
        {
            ban_reason = StringUtils.join(ArrayUtils.subarray(args, 2, args.length), " ") + " (" + sender.getName() + ")";
            bcast_msg.append("Read www.totalfreedom.net46.net46  You will be un-banned in 10mins!");
        }

        TFM_Util.adminAction(sender.getName(), bcast_msg.toString(), true);
        TFM_ServerInterface.banUsername(p.getName(), ban_reason, sender.getName(), ban_duration);
        TFM_ServerInterface.banIP(p.getAddress().getAddress().getHostAddress().trim(), ban_reason, sender.getName(), ban_duration);
        p.kickPlayer(sender.getName() + " - " + bcast_msg.toString());

        return true;
    }
}

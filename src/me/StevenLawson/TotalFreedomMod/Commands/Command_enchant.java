package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.Commands.CommandPermissions.ADMIN_LEVEL;
import me.StevenLawson.TotalFreedomMod.Commands.CommandPermissions.SOURCE_TYPE_ALLOWED;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandPermissions(level = ADMIN_LEVEL.OP, source = SOURCE_TYPE_ALLOWED.ONLY_IN_GAME, ignore_permissions = false)
public class Command_enchant extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length < 1)
        {
            return false;
        }

        ItemStack itemInHand = sender_p.getItemInHand();

        if (itemInHand == null)
        {
            playerMsg("You are holding an invalid item.");
            return true;
        }

        if (args[0].equalsIgnoreCase("list"))
        {
            boolean has_enchantments = false;

            StringBuilder possible_ench = new StringBuilder("Possible enchantments for held item: ");
            for (Enchantment ench : Enchantment.values())
            {
                if (ench.canEnchantItem(itemInHand))
                {
                    has_enchantments = true;
                    possible_ench.append(ench.getName()).append(", ");
                }
            }

            if (has_enchantments)
            {
                playerMsg(possible_ench.toString());
            }
            else
            {
                playerMsg("The held item has no enchantments.");
            }
        }
        else if (args[0].equalsIgnoreCase("addall"))
        {
            for (Enchantment ench : Enchantment.values())
            {
                if (ench.canEnchantItem(itemInHand))
                {
                    itemInHand.addEnchantment(ench, ench.getMaxLevel());
                }
            }

            playerMsg("Added all possible enchantments for this item.");
        }
        else if (args[0].equalsIgnoreCase("reset"))
        {
            for (Enchantment ench : itemInHand.getEnchantments().keySet())
            {
                itemInHand.removeEnchantment(ench);
            }

            playerMsg("Removed all enchantments.");
        }
        else
        {
            if (args.length < 2)
            {
                return false;
            }

            Enchantment ench = null;

            try
            {
                ench = Enchantment.getByName(args[1]);
            }
            catch (Exception ex)
            {
            }

            if (ench == null)
            {
                playerMsg(args[1] + " is an invalid enchantment for the held item. Type \"/enchant list\" for valid enchantments for this item.");
                return true;
            }

            if (args[0].equalsIgnoreCase("add"))
            {
                if (ench.canEnchantItem(itemInHand))
                {
                    itemInHand.addEnchantment(ench, ench.getMaxLevel());

                    playerMsg("Added enchantment: " + ench.getName());
                }
                else
                {
                    playerMsg("Can't use this enchantment on held item.");
                }
            }
            else if (args[0].equalsIgnoreCase("remove"))
            {
                itemInHand.removeEnchantment(ench);

                playerMsg("Removed enchantment: " + ench.getName());
            }
        }

        return true;
    }
}

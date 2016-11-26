package fr.vinetos.frp.commands;

import fr.vinetos.frp.ForcePlayer;
import fr.vinetos.frp.ForceResourcePack;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * File <b>FrpCommand</b> located on fr.vinetos.frp.commands
 * FrpCommand is a part of ForceResourcePack.
 * <p>
 * Copyright (c) 2016 Vinetos <b>https://vinetos.fr</b> and contributors.
 * <p>
 * ForceResourcePack is a free software: See the MIT License
 * Public License for more details (LICENSE.txt file)
 *
 * @author Vinetos
 *         Created the 23/11/2016 at 13:01
 */
// TODO: 23/11/2016 Create ArgsCommand + List of 'packs' (add, delete, modify)
public class FrpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (!sender.hasPermission("frp.command")) {
            sender.sendMessage("§cYou don't have the permission to perform this command !");
            return true;
        }

        if (args.length == 2 || args.length > 4) {
            showHelp(sender);
            return false;
        }

        if (args.length == 1) {
            if (!args[0].equalsIgnoreCase("reload")) {
                showHelp(sender);
                return false;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("frp.command.reload")) {
                    sender.sendMessage("§cYou don't have the permission to perform this command !");
                    return false;
                }
                ForceResourcePack.getConfigUtils().reloadConfig();
                sender.sendMessage("§7[§cForceResourcePack§7]§8 was reloaded !");
            }

            return true;
        }

        if (args.length == 3 || args.length == 4) {
            if (!args[0].equalsIgnoreCase("send") && !args[0].equalsIgnoreCase("config")) {
                showHelp(sender);
                return false;
            }

            if (args[0].equalsIgnoreCase("config")) {
                if (!sender.hasPermission("frp.command.config")) {
                    sender.sendMessage("§cYou don't have the permission to perform this command !");
                    return false;
                }
                if (!args[1].equalsIgnoreCase("get") && !args[1].equalsIgnoreCase("set")) {
                    showHelp(sender);
                    return true;
                }
                if (args.length == 3) {
                    // Send config key
                    sender.sendMessage(ForceResourcePack.getConfigUtils().get(args[2]));
                    return true;
                }
                sender.sendMessage(ForceResourcePack.getConfigUtils().set(args[2], args[3]));
                return true;
            }

            final Player target = Bukkit.getPlayerExact(args[1]);
            if (target == null) {
                sender.sendMessage("§cThis player cannot be found");
                return false;
            }

            if (sender instanceof Player) {
                if (target.hasPermission("frp.bypass") && ForceResourcePack.getConfigUtils().canBypass()) {
                    sender.sendMessage("§c" + target.getName() + " has permission to bypass your request !");
                    return false;
                }
            }

            final String url = ForceResourcePack.getConfigUtils().getPack(args[2]);
            if (url == null) {
                sender.sendMessage("§cThis pack cannot be found !");
                return false;
            }
            target.setResourcePack(url);
            ForcePlayer.findByPlayer(target).sendRequest();
            return true;
        }
        showHelp(sender);
        return false;

    }

    private void showHelp(CommandSender sender) {
        sender.sendMessage("§e/frp reload§f: Reload the plugin configuration");
        sender.sendMessage("§e/frp send <player> <packName>§f: Set a resource pack for a player");
        sender.sendMessage("§e/frp config <set/get> <key> [value]§f: Configure the plugin in game !");
    }


}

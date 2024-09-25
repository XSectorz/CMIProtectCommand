package net.xsapi.panat.cmiprotectcommand.commands;

import net.xsapi.panat.cmiprotectcommand.config.mainConfig;
import net.xsapi.panat.cmiprotectcommand.config.messagesConfig;
import net.xsapi.panat.cmiprotectcommand.utils.XSUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class command implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String arg, @NotNull String[] args) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;

            if(p.hasPermission("cmiprotectcmd.admin")) {

                if(args[0].equalsIgnoreCase("reload")) {

                    mainConfig.reload();
                    messagesConfig.reload();

                    p.sendMessage(XSUtils.decodeText(messagesConfig.getConfig().getString("reloaded")));
                    return false;
                }

            } else {
                p.sendMessage(XSUtils.decodeText(messagesConfig.getConfig().getString("permission")));
                return false;
            }

        }
        return false;
    }
}

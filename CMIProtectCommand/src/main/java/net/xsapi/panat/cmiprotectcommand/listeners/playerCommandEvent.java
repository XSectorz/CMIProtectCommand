package net.xsapi.panat.cmiprotectcommand.listeners;

import net.xsapi.panat.cmiprotectcommand.config.mainConfig;
import net.xsapi.panat.cmiprotectcommand.config.messagesConfig;
import net.xsapi.panat.cmiprotectcommand.utils.XSUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class playerCommandEvent implements Listener {

    @EventHandler
    public void onCommandProcess(PlayerCommandPreprocessEvent e) {

        Player p = e.getPlayer();

        if(!p.hasPermission("*")) {
            for(String cmd : mainConfig.getConfig().getStringList("blocked_commands")) {

                if(e.getMessage().equalsIgnoreCase(cmd) && !XSUtils.isWhitelist(p.getName())) {
                    String kickMsg = "";
                    for(String s : messagesConfig.getConfig().getStringList("kickMessage")) {
                        kickMsg += XSUtils.decodeText(s) + "\n";
                    }

                    p.kickPlayer(kickMsg);
                }

            }
        }

    }

}

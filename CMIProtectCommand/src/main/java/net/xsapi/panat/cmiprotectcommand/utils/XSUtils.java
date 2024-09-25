package net.xsapi.panat.cmiprotectcommand.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.xsapi.panat.cmiprotectcommand.config.mainConfig;
import net.xsapi.panat.cmiprotectcommand.config.messagesConfig;
import net.xsapi.panat.cmiprotectcommand.core;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

public class XSUtils {
    public static String decodeText(String str) {
        Component parsedMessage = MiniMessage.builder().build().deserialize(str);
        return LegacyComponentSerializer.legacyAmpersand().serialize(parsedMessage).replace("&","§");
    }

    public static boolean isWhitelist(String pName) {

        for(String list :  mainConfig.getConfig().getStringList("settings.whitelist")) {
            if(list.toLowerCase().equalsIgnoreCase(pName) || list.equalsIgnoreCase(pName)) {
                return true;
            }
        }


        return false;

    }

    public static void createRepeatTask() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(core.getPlugin(), new Runnable() {
            @Override
            public void run() {
                for(Player p : Bukkit.getOnlinePlayers()) {

                    for(String perm : mainConfig.getConfig().getStringList("blocked_permission")) {
                        if(p.hasPermission(perm) && !XSUtils.isWhitelist(p.getName())) {
                            String kickMsg = "";
                            for(String s : messagesConfig.getConfig().getStringList("banMessage")) {
                                kickMsg += XSUtils.decodeText(s) + "\n";
                            }

                            p.kickPlayer(kickMsg);
                            Bukkit.getBanList(BanList.Type.NAME).addBan(p.getName(),"[CMIProtectCMD] Checked found some exploits",null,"CMIProtectCMD");
                            Bukkit.getScheduler().runTask(core.getPlugin(), new Runnable() {
                                @Override
                                public void run() {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user " + p.getName() + " permission clear");
                                }
                            });
                            break;
                        }
                    }

                }
            }
        }, 0L, 20L);
    }


}

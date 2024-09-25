package net.xsapi.panat.cmiprotectcommand;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.node.NodeMutateEvent;
import net.luckperms.api.event.user.UserDataRecalculateEvent;
import net.luckperms.api.model.user.User;
import net.xsapi.panat.cmiprotectcommand.commands.commandLoader;
import net.xsapi.panat.cmiprotectcommand.config.configLoader;
import net.xsapi.panat.cmiprotectcommand.config.mainConfig;
import net.xsapi.panat.cmiprotectcommand.config.messagesConfig;
import net.xsapi.panat.cmiprotectcommand.listeners.eventLoader;
import net.xsapi.panat.cmiprotectcommand.utils.XSUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class core extends JavaPlugin {

    private static core plugin;
    public static LuckPerms luckPerms;

    public static core getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        new configLoader();
        new eventLoader();
        new commandLoader();

        XSUtils.createRepeatTask();

        luckPerms = LuckPermsProvider.get();
        EventBus eventBus = luckPerms.getEventBus();
        eventBus.subscribe(this, UserDataRecalculateEvent.class, event -> {
            User user = event.getUser();
            user.getNodes().forEach(node -> {

                for(String perm : mainConfig.getConfig().getStringList("blocked_permission")) {

                    if (node.getKey().equals(perm) && !XSUtils.isWhitelist(user.getUsername())) {

                        if(Bukkit.getPlayer(user.getUniqueId())!=null) {

                            Player target = Bukkit.getPlayer(user.getUniqueId());
                            String kickMsg = "";
                            for(String s : messagesConfig.getConfig().getStringList("banMessage")) {
                                kickMsg += XSUtils.decodeText(s) + "\n";
                            }

                            target.kickPlayer(kickMsg);
                        }
                        Bukkit.getBanList(BanList.Type.NAME).addBan(user.getUsername(),"[CMIProtectCMD] Checked found some exploits",null,"CMIProtectCMD");
                        Bukkit.getScheduler().runTask(core.getPlugin(), new Runnable() {
                            @Override
                            public void run() {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user " + user.getUsername() + " permission clear");
                            }
                        });
                    }
                }
            });
        });
    }

    @Override
    public void onDisable() {

    }
}

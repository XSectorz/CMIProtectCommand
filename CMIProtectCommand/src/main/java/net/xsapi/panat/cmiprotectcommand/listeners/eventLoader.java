package net.xsapi.panat.cmiprotectcommand.listeners;

import net.xsapi.panat.cmiprotectcommand.core;
import org.bukkit.Bukkit;

public class eventLoader {

    public eventLoader() {
        Bukkit.getPluginManager().registerEvents(new playerCommandEvent(), core.getPlugin());
    }

}

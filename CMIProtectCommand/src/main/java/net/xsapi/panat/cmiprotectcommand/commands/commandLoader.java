package net.xsapi.panat.cmiprotectcommand.commands;

import net.xsapi.panat.cmiprotectcommand.core;

public class commandLoader {

    public commandLoader() {

        core.getPlugin().getCommand("cmiprotectcmd").setExecutor(new command());
    }

}

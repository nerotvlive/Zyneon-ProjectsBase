package com.zyneonprojects.base.modules.essentials;

import com.zyneonprojects.base.modules.essentials.commands.AboutCommand;
import com.zyneonprojects.base.modules.essentials.commands.EssentialsCommand;
import com.zyneonprojects.base.modules.loader.modules.InbuiltModule;

public class EssentialsModule extends InbuiltModule {

    public EssentialsModule() {
        super("essentials","ESS");
    }

    @Override
    public boolean onLoad() {
        return super.onLoad();
    }

    @Override
    public boolean onEnable() {
        registerCommand(new AboutCommand(),"?","help","ver","version","icanhasbukkit");
        registerCommand(new EssentialsCommand(),"ess");
        return true;
    }

    @Override
    public boolean onDisable() {
        return super.onDisable();
    }

    @Override
    public boolean onUnload() {
        return super.onUnload();
    }

    @Override
    public String getDescription() {
        return "Inbuilt essentials features module";
    }
}

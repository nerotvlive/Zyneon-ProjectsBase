package com.zyneonprojects.base.modules.essentials;

import com.zyneonprojects.base.modules.essentials.commands.AboutCommand;
import com.zyneonprojects.base.modules.essentials.commands.EssentialsCommand;
import com.zyneonprojects.base.modules.essentials.commands.GiveCommand;
import com.zyneonprojects.base.modules.essentials.commands.SrlCommand;
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
        registerCommand(new AboutCommand(),"?","help");
        registerCommand(new EssentialsCommand(),"ess");
        registerCommand(new SrlCommand(),"stop","restart","rl","reload","end");
        registerCommand(new GiveCommand(),"get","item","getitem");
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

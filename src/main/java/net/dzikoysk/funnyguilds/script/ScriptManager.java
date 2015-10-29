package net.dzikoysk.funnyguilds.script;

import net.dzikoysk.funnyguilds.FunnyGuilds;
import net.dzikoysk.funnyguilds.util.IOUtils;
import org.panda_lang.panda.Panda;
import org.panda_lang.panda.PandaLoader;
import org.panda_lang.panda.PandaScript;

import java.io.File;

public class ScriptManager {

    private static ScriptManager instance;

    public ScriptManager() {
        instance = this;
    }

    public static ScriptManager getInstance() {
        if (instance == null)
            new ScriptManager();
        return instance;
    }

    public void start() {
		File home = IOUtils.getFile(FunnyGuilds.getInstance().getDataFolder() + File.separator + "scripts", true);
        File[] files = home.listFiles();
        if (files == null)
            return;
        for (File file : files) {
            if (file.isDirectory())
                continue;
            if (file.getName().endsWith(".pp")) {
                PandaScript ps = PandaLoader.loadSimpleScript(file);
                Panda.getInstance().addScript(ps);
            }
        }
        run();
    }

    public void run() {
        Panda.getInstance().reload();
    }

}

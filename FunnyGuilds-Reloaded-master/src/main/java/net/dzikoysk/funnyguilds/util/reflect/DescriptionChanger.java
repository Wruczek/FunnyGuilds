package net.dzikoysk.funnyguilds.util.reflect;

import net.dzikoysk.funnyguilds.FunnyGuilds;
import net.dzikoysk.funnyguilds.FunnyLog;
import org.bukkit.plugin.PluginDescriptionFile;

public class DescriptionChanger {

    private final PluginDescriptionFile desc;

    public DescriptionChanger(PluginDescriptionFile desc) {
        this.desc = desc;
    }

    public void name(String s) {
        if (s == null || s.isEmpty())
            return;
        try {
            Reflections.getPrivateField(desc.getClass(), "name").set(desc, s);
        } catch (Exception e) {
            if (FunnyLog.exception(e.getCause()))
                e.printStackTrace();
        }
    }
}

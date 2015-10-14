package net.dzikoysk.funnyguilds.data;

import net.dzikoysk.funnyguilds.FunnyGuilds;
import net.dzikoysk.funnyguilds.FunnyLog;
import net.dzikoysk.funnyguilds.util.thread.ActionType;
import net.dzikoysk.funnyguilds.util.thread.IndependentThread;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;

public class Manager {

    private static Manager instance;
    private volatile BukkitTask task = null;

    public Manager() {
        instance = this;
        Messages.getInstance();
        Settings.getInstance();
        //if(Settings.getInstance().mysql) DatabaseBasic.getInstance().load();
        //else Flat.getInstance().load();
        Data.getInstance();
    }

    public static void loadDefaultFiles(String[] files) {
        for (String file : files) {
            File cfg = new File(FunnyGuilds.getInstance().getDataFolder() + File.separator + file);
            if (!cfg.exists())
                FunnyGuilds.getInstance().saveResource(file, true);
        }
    }

    public static Manager getInstance() {
        if (instance != null)
            return instance;
        new Manager().start();
        return instance;
    }

    public void save() {
        if (Settings.getInstance().flat)
            try {
                //Flat.getInstance().save(false);
            } catch (Exception e) {
                FunnyLog.error("An error occurred while saving data to flat file! Caused by: Exception");
                if (FunnyLog.exception(e.getCause()))
                    e.printStackTrace();
            }
        if (Settings.getInstance().mysql)
            try {
                //DatabaseBasic.getInstance().save(false);
            } catch (Exception e) {
                FunnyLog.error("An error occurred while saving data to database! Caused by: Exception");
                if (FunnyLog.exception(e.getCause()))
                    e.printStackTrace();
            }
        //Data.getInstance().save();
    }

    public void start() {
        if (FunnyGuilds.getInstance().isDisabling())
            return;
        if (this.task != null)
            return;
        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(FunnyGuilds.getInstance(), new Runnable() {

            @Override
            public void run() {
                IndependentThread.action(ActionType.SAVE_DATA);
            }
        }, Settings.getInstance().dataInterval * 60 * 20, Settings.getInstance().dataInterval * 60 * 20);
    }

    public void stop() {
        if (this.task != null) {
            this.task.cancel();
            this.task = null;
        }
    }

    public Settings getSettings() {
        return Settings.getInstance();
    }

    public Messages getMessages() {
        return Messages.getInstance();
    }
}

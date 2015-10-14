package net.dzikoysk.funnyguilds;

import net.dzikoysk.funnyguilds.basic.Guild;
import net.dzikoysk.funnyguilds.basic.User;
import net.dzikoysk.funnyguilds.basic.util.GuildUtils;
import net.dzikoysk.funnyguilds.command.Commands;
import net.dzikoysk.funnyguilds.data.Manager;
import net.dzikoysk.funnyguilds.data.Settings;
import net.dzikoysk.funnyguilds.listener.*;
import net.dzikoysk.funnyguilds.listener.region.*;
import net.dzikoysk.funnyguilds.script.ScriptManager;
import net.dzikoysk.funnyguilds.util.IOUtils;
import net.dzikoysk.funnyguilds.util.Reloader;
import net.dzikoysk.funnyguilds.util.metrics.MetricsCollector;
import net.dzikoysk.funnyguilds.util.reflect.DescriptionChanger;
import net.dzikoysk.funnyguilds.util.reflect.EntityUtil;
import net.dzikoysk.funnyguilds.util.reflect.PacketExtension;
import net.dzikoysk.funnyguilds.util.runnable.AsynchronouslyRepeater;
import net.dzikoysk.funnyguilds.util.runnable.ScoreboardStack;
import net.dzikoysk.funnyguilds.util.runnable.Ticking;
import net.dzikoysk.funnyguilds.util.thread.IndependentThread;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import static net.dzikoysk.funnyguilds.FunnyLog.*;

import java.io.InputStream;

public class FunnyGuilds extends JavaPlugin {

    private static FunnyGuilds instance;
    private boolean disabling;

    public static FunnyGuilds getInstance() {
        if(instance == null) throw new IllegalStateException("Cannot get FunnyGuild instance before invoke onLoad()");
        return instance;
    }

    public String getVersion() {
        return getDescription().getVersion();
    }

    @Override
    public void onLoad() {
        instance = this;

        new Reloader().init();
        new DescriptionChanger(getDescription()).name(Settings.getInstance().pluginName);
        new Commands().register();
    }

    @Override
    public void onEnable() {
        new ScoreboardStack().start();
        new IndependentThread().start();
        new Manager().start();
        new AsynchronouslyRepeater().start();
        new Ticking().start();
        new MetricsCollector().start();
        new ScriptManager().start();

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PacketReceiveListener(), this);

        pm.registerEvents(new EntityDamageListener(), this);
        pm.registerEvents(new PlayerInteractEntityListener(), this);
        pm.registerEvents(new AsyncPlayerChatListener(), this);
        pm.registerEvents(new PlayerDeathListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerLoginListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);

        pm.registerEvents(new BlockBreakListener(), this);
        pm.registerEvents(new BlockIgniteListener(), this);
        pm.registerEvents(new BlockPlaceListener(), this);
        pm.registerEvents(new BucketActionListener(), this);
        pm.registerEvents(new EntityExplodeListener(), this);
        pm.registerEvents(new PlayerCommandListener(), this);
        pm.registerEvents(new PlayerInteractListener(), this);

        if (Settings.getInstance().eventMove)
            pm.registerEvents(new PlayerMoveListener(), this);
        if (Settings.getInstance().eventPhysics)
            pm.registerEvents(new BlockPhysicsListener(), this);

        patch();
        checkUpdate();
        info("~ Created by & Dzikoysk");
    }

    @Override
    public void onDisable() {
        disabling = true;

        EntityUtil.despawn();
        PacketExtension.unregisterFunnyGuildsChannel();

        AsynchronouslyRepeater.getInstance().stop();
        Manager.getInstance().stop();
        Manager.getInstance().save();

        instance = null;
    }

    private void checkUpdate() {
        Thread thread = new Thread() {

            @Override
            public void run() {
                String latest = IOUtils.getContent("http://www.dzikoysk.net/projects/funnyguilds/latest.info");
                if (latest == null || latest.isEmpty())
                    update("Failed to check the new version of FunnyGuilds.");
                else if (latest.equalsIgnoreCase(getVersion()))
                    update("You have a current version of FunnyGuilds.");
                else {
                    update("");
                    update("Available is new version of FunnyGuilds!");
                    update("Current: " + getVersion());
                    update("Latest: " + latest);
                    update("");
                }
            }
        };
        thread.start();
    }

    private void patch() {

        new BukkitRunnable() {
            @Override public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    PacketExtension.registerPlayer(player);
                }
            }
        }.runTaskLater(this, 1L);

        for (Player player : Bukkit.getOnlinePlayers()) {
            User user = User.get(player);
            user.getScoreboard();
            user.getDummy();
            user.getRank();
        }
        for (Guild guild : GuildUtils.getGuilds()) {
            EntityUtil.spawn(guild);
            guild.updateRank();
        }
    }

    @Override
    public InputStream getResource(String s) {
        return super.getResource(s);
    }

    public boolean isDisabling() {
        return disabling;
    }
}

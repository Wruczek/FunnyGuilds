package net.dzikoysk.funnyguilds.util.runnable;

import net.dzikoysk.funnyguilds.FunnyGuilds;
import net.dzikoysk.funnyguilds.FunnyLog;
import org.bukkit.Bukkit;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class ThreadMonitor implements Runnable {

    public void start() {
        Bukkit.getScheduler().runTaskTimer(FunnyGuilds.getInstance(), this, 0, 200);
    }

    @Override
    public void run() {
        ThreadMXBean tmxb = ManagementFactory.getThreadMXBean();
        long full = 0;
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            full += tmxb.getThreadCpuTime(t.getId());
        }
        FunnyLog.info("================================");
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (tmxb.getThreadCpuTime(t.getId()) > 0) {
                long l = (tmxb.getThreadCpuTime(t.getId()) * 100L) / full;
                if (l > 0.0)
                    FunnyLog.info(t.getName() + ": " + l + "%");
            }
        }
        FunnyLog.info("================================");
    }

}

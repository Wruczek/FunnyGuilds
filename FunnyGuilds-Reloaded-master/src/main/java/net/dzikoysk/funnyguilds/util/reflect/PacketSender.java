package net.dzikoysk.funnyguilds.util.reflect;

import net.dzikoysk.funnyguilds.FunnyGuilds;
import net.dzikoysk.funnyguilds.FunnyLog;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;

public class PacketSender {

    private static final String packageName = Bukkit.getServer().getClass().getPackage().getName();
    private static final String version = packageName.substring(packageName.lastIndexOf(".") + 1);

    public static void sendPacket(Player player, Object... os) {
        sendPacket(Collections.singleton(player), os);
    }

    public static void sendPacket(Collection<? extends Player> players, Object... os) {
        try {
            Class<?> packetClass = Class.forName("net.minecraft.server." + version + ".Packet");
            Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");

            for (Player p : players) {
                Object cp = craftPlayer.cast(p);
                Object handle = craftPlayer.getMethod("getHandle").invoke(cp);
                Object con = handle.getClass().getField("playerConnection").get(handle);
                Method method = con.getClass().getMethod("sendPacket", packetClass);
                for (Object o : os) {
                    if (o == null)
                        continue;
                    method.invoke(con, o);
                }
            }
        } catch (Exception e) {
            if (FunnyLog.exception(e.getCause()))
                e.printStackTrace();
        }
    }

}

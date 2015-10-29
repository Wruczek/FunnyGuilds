package net.dzikoysk.funnyguilds.util.element;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import net.dzikoysk.funnyguilds.FunnyGuilds;
import net.dzikoysk.funnyguilds.FunnyLog;
import net.dzikoysk.funnyguilds.basic.OfflineUser;
import net.dzikoysk.funnyguilds.basic.User;
import net.dzikoysk.funnyguilds.util.reflect.PacketSender;
import net.dzikoysk.funnyguilds.util.reflect.transition.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PlayerListManager {

    private static String[] scheme = new String[60];
    private static boolean enable;
    private static int ping;
    private static boolean patch;

    public static void updatePlayers() {
        for (Player player : Bukkit.getOnlinePlayers())
            User.get(player).getPlayerList().send();
    }

    public static void send(Player player) {
        if (!enable)
            return;
        User user = User.get(player);
        Scoreboard sb = user.getScoreboard();
        PlayerList pl = user.getPlayerList();
        String[] prefix = pl.getPrefix();
        String[] suffix = pl.getSuffix();
        for (int i = 0; i < 60; i++) {
            if (scheme[i] == null) {
                //Data.getPlayerListFile().delete();
                scheme = PlayerListScheme.uniqueFields();
            }
            String s = scheme[i];
            if (s == null)
                continue;
            Team team = sb.getTeam(s);
            if (team == null) {
                team = sb.registerNewTeam(s);
                team.addPlayer(new OfflineUser(s));
            }
            if (prefix[i] != null)
                team.setPrefix(prefix[i]);
            if (suffix[i] != null)
                team.setSuffix(suffix[i]);
        }
        if (!pl.getInit()) {
            Collection<String> ss = Collections2.transform(Bukkit.getOnlinePlayers(), new Function<Player, String>() {
                @Nullable @Override public String apply(Player player) {
                    return player.getPlayerListName();
                }
            });

            pl.init(true);
            PacketSender.sendPacket(player, packets(ss, false));
            PacketSender.sendPacket(player, packets(scheme, true));
        }
        if (patch) {
            Collection<String> ss = Collections2.transform(Bukkit.getOnlinePlayers(), new Function<Player, String>() {
                @Nullable @Override public String apply(Player player) {
                    return player.getPlayerListName();
                }
            });
            PacketSender.sendPacket(player, packets(ss, false));
        }
        try {
            player.setScoreboard(sb);
        } catch (IllegalStateException e) {
            FunnyLog.warning(
                    "[PlayerList] java.lang.IllegalStateException: Cannot set scoreboard for invalid CraftPlayer (" + player.getClass() + ")");
        }
        user.setScoreboard(sb);
    }

    private static Object[] packets(String[] ss, boolean b) {
        Object[] packets = new Object[ss.length];
        for (int i = 0; i < ss.length; i++)
            packets[i] = PacketPlayOutPlayerInfo.getPacket(ss[i], b, ping);
        return packets;
    }

    private static Object[] packets(Collection<String> ss, boolean b) {
        Object[] packets = new Object[ss.size()];
        int i=0;
        for (String s : ss)
            packets[i++] = PacketPlayOutPlayerInfo.getPacket(s, b, ping);
        return packets;
    }

    public static void scheme(String[] ss) {
        String[] clone = ss.clone();
        for (int i = 0; i < clone.length; i++)
            if (clone[i] != null)
                clone[i] = ChatColor.translateAlternateColorCodes('.', clone[i]);
        scheme = clone;
    }

    public static String[] scheme() {
        return scheme;
    }

    public static void enable(boolean e) {
        enable = e;
    }

    public static void ping(int i) {
        ping = i;
    }

    public static void patch(boolean p) {
        patch = p;
    }
}

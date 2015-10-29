package net.dzikoysk.funnyguilds.command.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import net.dzikoysk.funnyguilds.basic.Guild;
import net.dzikoysk.funnyguilds.basic.util.GuildUtils;
import net.dzikoysk.funnyguilds.command.util.Executor;
import net.dzikoysk.funnyguilds.data.Messages;
import net.dzikoysk.funnyguilds.system.ban.BanUtils;
import net.dzikoysk.funnyguilds.util.Parser;
import net.dzikoysk.funnyguilds.util.StringUtils;

public class AxcBan implements Executor {

    @Override
    public void execute(CommandSender sender, String[] args) {
        Messages m = Messages.getInstance();

        if (!sender.hasPermission("funnyguilds.admin")) {
            sender.sendMessage(m.getMessage("permission"));
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(StringUtils.colored("&cPodaj tag gildii!"));
            return;
        } else if (args.length < 2) {
            sender.sendMessage(StringUtils.colored("&cPodaj czas na jaki ma byc zbanowana gildia!"));
            return;
        } else if (args.length < 3) {
            sender.sendMessage(StringUtils.colored("&cPodaj powod!"));
            return;
        }

        String tag = args[0];
        String ts = args[1];

        StringBuilder sb = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            sb.append(args[i]);
            sb.append(" ");
        }
        String reason = sb.toString();

        if (!GuildUtils.tagExists(tag)) {
            sender.sendMessage(StringUtils.colored("&cGildia o takim tagu nie istnieje!"));
            return;
        }

        Guild guild = GuildUtils.byTag(tag);
        if (guild.isBanned()) {
            sender.sendMessage(StringUtils.colored("&cTa gildia jest juz zbanowana!"));
            return;
        }

        long time = Parser.parseTime(ts);
        if (time < 1) {
            sender.sendMessage(StringUtils.colored("&cPodano nieprawidlowy czas!"));
            return;
        }

        BanUtils.ban(guild, time, reason);
        sender.sendMessage(StringUtils.colored("&7Zbanowano gildie &b" + guild.getName() + " &7na okres &b" + ts + "&7!"));
        Bukkit.broadcastMessage(Messages.getInstance().getMessage("broadcastBan")
                        .replace("{GUILD}", guild.getName())
                        .replace("{TAG}", guild.getTag())
                        .replace("{REASON}", StringUtils.colored(reason))
                        .replace("{TIME}", ts)
        );
    }

}

package net.dzikoysk.funnyguilds.command.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import net.dzikoysk.funnyguilds.basic.Guild;
import net.dzikoysk.funnyguilds.basic.util.GuildUtils;
import net.dzikoysk.funnyguilds.command.util.Executor;
import net.dzikoysk.funnyguilds.data.Messages;
import net.dzikoysk.funnyguilds.system.ban.BanUtils;
import net.dzikoysk.funnyguilds.util.StringUtils;

public class AxcUnban implements Executor {

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
        }

        String tag = args[0];
        if (!GuildUtils.tagExists(tag)) {
            sender.sendMessage(StringUtils.colored("&cGildia o takim tagu nie istnieje!"));
            return;
        }

        Guild guild = GuildUtils.byTag(tag);
        if (!guild.isBanned()) {
            sender.sendMessage(StringUtils.colored("&cTa gildia nie jest zbanowana!"));
            return;
        }

        BanUtils.unban(guild);
        sender.sendMessage(StringUtils.colored("&7Odbanowano gildie &a" + guild.getName() + "&7!"));

        Bukkit.broadcastMessage(Messages.getInstance().getMessage("broadcastUnban")
                        .replace("{GUILD}", guild.getName())
                        .replace("{TAG}", guild.getTag())
        );
    }

}

package net.dzikoysk.funnyguilds.command.admin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dzikoysk.funnyguilds.basic.Guild;
import net.dzikoysk.funnyguilds.basic.User;
import net.dzikoysk.funnyguilds.basic.util.GuildUtils;
import net.dzikoysk.funnyguilds.command.util.Executor;
import net.dzikoysk.funnyguilds.data.Messages;

public class AxcDelete implements Executor {

    @Override
    public void execute(CommandSender sender, String[] args) {

        Messages m = Messages.getInstance();

        if (!sender.hasPermission("funnyguilds.admin")) {
            sender.sendMessage(m.getMessage("permission"));
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Podaj tag gildii!");
            return;
        }

        String tag = args[0];

        if (!GuildUtils.tagExists(tag)) {
            sender.sendMessage(ChatColor.RED + "Nie ma gildii o takim tagu!");
            return;
        }

        Guild guild = GuildUtils.byTag(tag);

        User o = guild.getOwner();
        String name = guild.getName();
        tag = guild.getTag();

        GuildUtils.deleteGuild(guild);

        sender.sendMessage(m.getMessage("deleteSuccessful")
                        .replace("{GUILD}", name)
                        .replace("{TAG}", tag)
        );

        Player owner = Bukkit.getPlayer(o.getName());
        if (owner != null)
            owner.sendMessage(
                    ChatColor.RED + "Twoja gildia zostala rozwiazana przez " + ChatColor.GRAY + sender.getName()
            );

        Bukkit.getServer().broadcastMessage(m.getMessage("broadcastDelete")
                        .replace("{PLAYER}", sender.getName())
                        .replace("{GUILD}", name)
                        .replace("{TAG}", tag)
        );
    }

}

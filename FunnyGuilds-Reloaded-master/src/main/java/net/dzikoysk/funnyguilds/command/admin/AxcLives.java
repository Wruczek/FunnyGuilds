package net.dzikoysk.funnyguilds.command.admin;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.dzikoysk.funnyguilds.basic.Guild;
import net.dzikoysk.funnyguilds.basic.util.GuildUtils;
import net.dzikoysk.funnyguilds.command.util.Executor;
import net.dzikoysk.funnyguilds.data.Messages;

public class AxcLives implements Executor {

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

        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Podaj ilosc zyc!");
            return;
        }

        Guild guild = GuildUtils.byTag(args[0]);
        if (guild == null) {
            sender.sendMessage(ChatColor.RED + "Nie ma gildii o takim tagu!");
            return;
        }

        int lives = 0;
        try {
            lives = Integer.valueOf(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Nieprawidlowa ilosc zyc! Nieznana jest liczba: " + ChatColor.DARK_RED + args[1]);
            return;
        }

        guild.setLives(lives);
        sender.sendMessage(
                ChatColor.GRAY + "Ustawiono " + ChatColor.AQUA + lives + " zyc " + ChatColor.GRAY + "dla gildii " + ChatColor.AQUA + guild.getTag());
        return;
    }

}

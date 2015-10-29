package net.dzikoysk.funnyguilds.command.admin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dzikoysk.funnyguilds.basic.Guild;
import net.dzikoysk.funnyguilds.basic.OfflineUser;
import net.dzikoysk.funnyguilds.basic.User;
import net.dzikoysk.funnyguilds.command.util.Executor;
import net.dzikoysk.funnyguilds.data.Messages;
import net.dzikoysk.funnyguilds.util.thread.ActionType;
import net.dzikoysk.funnyguilds.util.thread.IndependentThread;

public class AxcKick implements Executor {

    @Override
    public void execute(CommandSender sender, String[] args) {

        Messages m = Messages.getInstance();

        if (!sender.hasPermission("funnyguilds.admin")) {
            sender.sendMessage(m.getMessage("permission"));
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Podaj nick gracza!");
            return;
        }

        User user = User.get(args[0]);
        OfflineUser offline = user.getOfflineUser();
        Player p = Bukkit.getPlayer(user.getName());

        if (!user.hasGuild()) {
            sender.sendMessage(ChatColor.RED + "Ten gracz nie ma gildii!");
            return;
        }

        if (user.isOwner()) {
            sender.sendMessage(ChatColor.RED + "Ten gracz jest zalozycielem gildii, nie mozna go wyrzucic!");
            return;
        }

        Guild guild = user.getGuild();

        IndependentThread.action(ActionType.PREFIX_GLOBAL_REMOVE_PLAYER, offline);

        guild.removeMember(user);
        user.removeGuild();

        if (p != null)
            IndependentThread.action(ActionType.PREFIX_GLOBAL_UPDATE_PLAYER, sender);

        sender.sendMessage(m.getMessage("kickToOwner")
                        .replace("{PLAYER}", user.getName())
        );

        if (p != null)
            p.sendMessage(m.getMessage("kickToPlayer")
                            .replace("{GUILD}", guild.getName())
            );

        Bukkit.broadcastMessage(
                m.getMessage("broadcastKick")
                        .replace("{PLAYER}", user.getName())
                        .replace("{GUILD}", guild.getName())
                        .replace("{TAG}", guild.getTag())
        );
    }

}

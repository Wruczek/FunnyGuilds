package net.dzikoysk.funnyguilds.command.admin;

import net.dzikoysk.funnyguilds.basic.Guild;
import net.dzikoysk.funnyguilds.basic.OfflineUser;
import net.dzikoysk.funnyguilds.basic.User;
import net.dzikoysk.funnyguilds.basic.util.GuildUtils;
import net.dzikoysk.funnyguilds.command.util.Executor;
import net.dzikoysk.funnyguilds.data.Messages;
import net.dzikoysk.funnyguilds.util.thread.ActionType;
import net.dzikoysk.funnyguilds.util.thread.IndependentThread;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AxcAdd implements Executor {

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
            sender.sendMessage(ChatColor.RED + "Podaj nick gracza!");
            return;
        }

        String tag = args[0];
        User user = User.get(args[1]);
        OfflineUser offline = user.getOfflineUser();

        if (user.hasGuild()) {
            sender.sendMessage(ChatColor.RED + "Ten gracz ma juz gildie!");
            return;
        }

        Guild guild = GuildUtils.byTag(tag);
        if (guild == null) {
            sender.sendMessage(ChatColor.RED + "Taka gildia nie istnieje!");
            return;
        }

        guild.addMember(user);
        user.setGuild(guild);

        IndependentThread.action(ActionType.PREFIX_GLOBAL_ADD_PLAYER, offline);

        if (offline.isOnline())
            Bukkit.getPlayer(user.getName()).sendMessage(m.getMessage("joinToMember")
                            .replace("{GUILD}", guild.getName())
                            .replace("{TAG}", guild.getTag())
            );

        Player owner = Bukkit.getPlayer(guild.getOwner().getName());
        if (owner != null)
            owner.sendMessage(m.getMessage("joinToOwner")
                            .replace("{PLAYER}", user.getName())
            );

        Bukkit.broadcastMessage(m.getMessage("broadcastJoin")
                        .replace("{PLAYER}", user.getName())
                        .replace("{GUILD}", guild.getName())
                        .replace("{TAG}", tag)
        );
    }

}

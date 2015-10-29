package net.dzikoysk.funnyguilds.command.admin;

import net.dzikoysk.funnyguilds.command.util.Executor;
import net.dzikoysk.funnyguilds.data.Messages;
import net.dzikoysk.funnyguilds.data.Settings;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AxcMain implements Executor {

    @Override
    public void execute(CommandSender sender, String[] args) {

        Messages m = Messages.getInstance();

        if (!sender.hasPermission("funnyguilds.admin")) {
            sender.sendMessage(m.getMessage("permission"));
            return;
        }

        Settings c = Settings.getInstance();
        sender.sendMessage(ChatColor.AQUA + "/" + c.axcAdd + " [tag] [nick] " + ChatColor.GRAY + "- Dodaje gracza do gildii");
        sender.sendMessage(ChatColor.AQUA + "/" + c.axcDelete + " [tag] " + ChatColor.GRAY + "- Usuwa gildie");
        sender.sendMessage(ChatColor.AQUA + "/" + c.axcKick + " [nick] " + ChatColor.GRAY + "- Wyrzuca gracza z gildii");
        
        if(sender instanceof Player)
        	sender.sendMessage(ChatColor.AQUA + "/" + c.axcTeleport + " [tag] " + ChatColor.GRAY + "- Teleportuje do bazy gildii");
        
        sender.sendMessage(ChatColor.AQUA + "/" + c.axcPoints + " [nick] [points] " + ChatColor.GRAY + "- Ustawia punkty gracza");
        sender.sendMessage(ChatColor.AQUA + "/" + c.axcKills + " [nick] [kills] " + ChatColor.GRAY + "- Ustawia ilosc zabojstw gracza");
        sender.sendMessage(ChatColor.AQUA + "/" + c.axcDeaths + " [nick] [deaths] " + ChatColor.GRAY + "- Ustawia ilosc smierci gracza");
        sender.sendMessage(ChatColor.AQUA + "/" + c.axcBan + " [tag] [czas] [powod] " + ChatColor.GRAY + "- Banuje gildie na okreslony czas");
        sender.sendMessage(ChatColor.AQUA + "/" + c.axcUnban + " [tag] " + ChatColor.GRAY + "- Odbanowywuje gildie");
        sender.sendMessage(ChatColor.AQUA + "/" + c.axcLives + " [tag] [zycia] " + ChatColor.GRAY + "- Ustawia ilosc zyc gildii");
        
        if(sender instanceof Player)
        	sender.sendMessage(ChatColor.AQUA + "/" + c.axcMove + " [tag]" + ChatColor.GRAY + "- Przenosi teren gildii");
        
        sender.sendMessage(ChatColor.AQUA + "/" + c.axcValidity + " [tag] [czas] " + ChatColor.GRAY + "- Przedluza waznosc gildii o podany czas");
        sender.sendMessage(ChatColor.AQUA + "/" + c.axcName + " [tag] [nazwa] " + ChatColor.GRAY + "- Zmienia nazwe gildii");
    }
}

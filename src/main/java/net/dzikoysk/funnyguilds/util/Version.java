package net.dzikoysk.funnyguilds.util;

import org.bukkit.ChatColor;
import org.bukkit.Note.Tone;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dzikoysk.funnyguilds.FunnyGuilds;
import net.dzikoysk.funnyguilds.FunnyLog;

public class Version {

	public static void check(final CommandSender commandSender, final boolean messageOnlyOnNewVersion) {
		if (!(commandSender.hasPermission("funnyguilds.admin") || commandSender.isOp()))
			return;

		Thread thread = new Thread() {
			
			@Override
			public void run() {
				// TODO: zmiana adresu
				String latest = IOUtils.getContent("http://www.dzikoysk.net/projects/funnyguilds/latest.info").trim();

				if (latest == null) {
					// TODO: jakies powiadomienie w konsoli?
					return;
				}

				if (!latest.equalsIgnoreCase(FunnyGuilds.getVersion())) {
					commandSender.sendMessage("");
					commandSender.sendMessage(ChatColor.DARK_GRAY + "-----------------------------------");
					commandSender.sendMessage(ChatColor.GRAY + "Dostepna jest nowa wersja " + ChatColor.AQUA + "FunnyGuilds" + ChatColor.GRAY + "!");
					commandSender.sendMessage(ChatColor.GRAY + "Obecna: " + ChatColor.AQUA + FunnyGuilds.getVersion());
					commandSender.sendMessage(ChatColor.GRAY + "Najnowsza: " + ChatColor.AQUA + latest);
					commandSender.sendMessage(ChatColor.DARK_GRAY + "-----------------------------------");
					commandSender.sendMessage("");

					if (commandSender instanceof Player) {
						Player player = (Player) commandSender;
						int interval = 225;

						for (int i = 0; i < 4; i++) {
							try {
								NotePitch.play(player, 3, Tone.C);
								NotePitch.play(player, 4, Tone.C);
								Thread.sleep(interval);
							} catch (Exception e) {
								if (FunnyLog.exception(e.getCause()))
									e.printStackTrace();
							}
						}
						NotePitch.play(player, 3, Tone.G);
					}
				} else if(!messageOnlyOnNewVersion) {
					commandSender.sendMessage(ChatColor.GRAY + "Uzywasz najnowszej wersji FunnyGuilds.");
				}
			}
		};
		thread.start();
	}
}

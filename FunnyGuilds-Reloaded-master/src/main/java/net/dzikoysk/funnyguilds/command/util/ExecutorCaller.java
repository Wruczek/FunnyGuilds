package net.dzikoysk.funnyguilds.command.util;

import net.dzikoysk.funnyguilds.FunnyGuilds;
import net.dzikoysk.funnyguilds.FunnyLog;
import net.dzikoysk.funnyguilds.data.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExecutorCaller implements CommandExecutor, TabExecutor {

    private static final List<ExecutorCaller> ecs = new ArrayList<>();

    private String overriding;
    private Executor executor;
    private String permission;
    private List<String> aliases;
    private String[] secondary;
    private List<ExecutorCaller> executors = new ArrayList<>();

    public ExecutorCaller(Executor exc, String command, String perm, List<String> aliases) {
        if (exc == null || command == null)
            return;

        this.executor = exc;
        this.permission = perm;
        if (aliases != null && aliases.size() > 0)
            this.aliases = aliases;
        else
            this.aliases = null;

        String[] splited = command.split("\\s+");
        this.overriding = splited[0];
        if (splited.length > 1) {
            this.secondary = new String[splited.length - 1];
            for (int i = 1; i < splited.length; i++)
                this.secondary[i - 1] = splited[i];
        } else
            this.secondary = null;

        for (ExecutorCaller ec : ecs) {
            if (ec.overriding.equalsIgnoreCase(this.overriding)) {
                ec.executors.add(this);
                return;
            }
        }
        this.register();
        executors.add(this);
        ecs.add(this);
    }

    private boolean call(CommandSender sender, Command cmd, String[] args) {
        if (!cmd.getName().equalsIgnoreCase(this.overriding))
            return false;
        ExecutorCaller main = null;
        for (ExecutorCaller ec : this.executors) {
            if (ec.secondary != null) {
                if (ec.secondary.length > args.length)
                    continue;
                boolean sec = false;
                for (int i = 0; i < ec.secondary.length; i++) {
                    if (!ec.secondary[i].equalsIgnoreCase(args[i])) {
                        sec = true;
                        break;
                    }
                }
                if (sec)
                    continue;
                args = Arrays.copyOfRange(args, ec.secondary.length, args.length);
            } else {
                main = ec;
                continue;
            }
            if (sender instanceof Player) {
                if (ec.permission != null && !sender.hasPermission(ec.permission)) {
                    sender.sendMessage(Messages.getInstance().getMessage("permission"));
                    return true;
                }
            }
            ec.executor.execute(sender, args);
            return true;
        }
        main.executor.execute(sender, args);
        return true;
    }

    private void register() {
        try {
            Performer p = new Performer(this.overriding);
            if (this.aliases != null)
                p.setAliases(this.aliases);
            p.setPermissionMessage(Messages.getInstance().getMessage("permission"));
            Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            CommandMap cmap = (CommandMap) f.get(Bukkit.getServer());
            cmap.register("", p);
            p.setExecutor(this);
        } catch (Exception e) {
            if (FunnyLog.exception(e.getCause()))
                e.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return call(sender, cmd, args);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (this.secondary != null)
            return Arrays.asList(this.secondary);
        else
            return null;
    }
}

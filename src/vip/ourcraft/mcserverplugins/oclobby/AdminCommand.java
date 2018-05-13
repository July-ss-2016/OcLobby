package vip.ourcraft.mcserverplugins.oclobby;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

/**
 * Created by July on 2018/04/30.
 */
public class AdminCommand implements CommandExecutor {
    private OcLobby plugin;

    public AdminCommand(OcLobby plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String lable, String[] args) {
        if (!cs.hasPermission("oclobby.admin")) {
            cs.sendMessage("no per!");
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.loadConfig();
            cs.sendMessage("ok.");
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("setspawn")) {
            if (!(cs instanceof Player)) {
                cs.sendMessage("cs must be player!");
                return true;
            }

            Player player = (Player) cs;

            cs.sendMessage(setSpawnLoc(player.getLocation()) ? "ok." : "failed.");
            return true;
        }

        return false;
    }

    private boolean setSpawnLoc(Location loc) {
        File file = new File(plugin.getDataFolder(), "config.yml");
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection locSection = yml.getConfigurationSection("spawn_loc");

        locSection.set("w", loc.getWorld().getName());
        locSection.set("x", loc.getX());
        locSection.set("y", loc.getY());
        locSection.set("z", loc.getZ());
        locSection.set("yaw", loc.getYaw());
        locSection.set("pitch", loc.getPitch());

        try {
            yml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        plugin.loadConfig();
        return true;
    }
}

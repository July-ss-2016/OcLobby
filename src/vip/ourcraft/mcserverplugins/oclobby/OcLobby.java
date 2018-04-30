package vip.ourcraft.mcserverplugins.oclobby;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import vip.ourcraft.mcserverplugins.oclobby.listeners.AuthMeListener;
import vip.ourcraft.mcserverplugins.oclobby.listeners.BukkitListener;

/**
 * Created by July on 2018/04/30.
 * 包含的功能：强制传送指定点、Authme登录后粒子展示，首次登录礼包
 */
public class OcLobby extends JavaPlugin {
    private static OcLobby instance;
    private Settings settings;
    private boolean isCreeperKitsEnabled;

    public void onEnable() {
        instance = this;
        this.settings = new Settings();
        this.isCreeperKitsEnabled = Bukkit.getPluginManager().isPluginEnabled("CreeperKits");

        loadConfig();

        if (Bukkit.getPluginManager().isPluginEnabled("AuthMe")) {
            Bukkit.getPluginManager().registerEvents(new AuthMeListener(), this);
        }

        Bukkit.getPluginManager().registerEvents(new BukkitListener(this), this);
        getCommand("oclobby").setExecutor(new AdminCommand());
    }

    public static OcLobby getInstance() {
        return instance;
    }

    public boolean getCreeperKitsEnabled() {
        return isCreeperKitsEnabled;
    }

    public Settings getSettings() {
        return settings;
    }

    private void loadConfig() {
        saveDefaultConfig();
        reloadConfig();

        FileConfiguration config = getConfig();

        settings.setDisplayLoginInEffect(config.getBoolean("is_display_login_in_effect"));
        settings.setForceSpawnLoc(new Location(Bukkit.getWorld(config.getString("force_spawn_loc.w")), config.getDouble("force_spawn_loc.x"), config.getDouble("force_spawn_loc.y"), config.getDouble("force_spawn_loc.z"),
                (float) config.getDouble("force_spawn_loc.x"), (float) config.getDouble("force_spawn_loc.x")));
        settings.setFirstLoginKits(config.getStringList("first_login_kits"));
    }
}

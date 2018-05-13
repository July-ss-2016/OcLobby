package vip.ourcraft.mcserverplugins.oclobby;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import vip.ourcraft.mcserverplugins.oclobby.listeners.AuthMeListener;
import vip.ourcraft.mcserverplugins.oclobby.listeners.BukkitListener;
import vip.ourcraft.mcserverplugins.oclobby.listeners.QrCodeListener;
import vip.ourcraft.mcserverplugins.oclobby.tasks.BossBarUpdateTask;
import vip.ourcraft.mcserverplugins.oclobby.tasks.TimeLockTask;
import vip.ourcraft.mcserverplugins.oclobby.utils.BukkitUtil;

import java.util.List;

/**
 * Created by July on 2018/04/30.
 * 包含的功能：强制传送指定点、Authme登录后粒子展示，首次登录礼包
 */
public class OcLobby extends JavaPlugin {
    private static OcLobby instance;
    private Settings settings;
    private boolean isCreeperKitsEnabled;
    private BossBarUpdateTask bossBarUpdateTask;

    public void onEnable() {
        instance = this;
        this.settings = new Settings();

        loadConfig();

        this.isCreeperKitsEnabled = Bukkit.getPluginManager().isPluginEnabled("CreeperKits");
        this.bossBarUpdateTask = new BossBarUpdateTask(this);

        if (Bukkit.getPluginManager().isPluginEnabled("AuthMe")) {
            Bukkit.getPluginManager().registerEvents(new AuthMeListener(this), this);
        }

        Bukkit.getPluginManager().registerEvents(new BukkitListener(this), this);
        Bukkit.getPluginManager().registerEvents(new QrCodeListener(), this);

        getCommand("oclobby").setExecutor(new AdminCommand(this));

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new TimeLockTask(this), 0L, 20L);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, bossBarUpdateTask, 0L, 40L);

        for (Player player : Bukkit.getOnlinePlayers()) {
            bossBarUpdateTask.addPlayer(player);
        }
    }

    public BossBarUpdateTask getBossBarUpdateTask() {
        return bossBarUpdateTask;
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

    public void loadConfig() {
        saveDefaultConfig();
        reloadConfig();

        FileConfiguration config = getConfig();

        settings.setDisplayLoginInEffect(config.getBoolean("is_display_login_in_effect"));
        settings.setSpawnLoc(new Location(Bukkit.getWorld(config.getString("spawn_loc.w")), config.getDouble("spawn_loc.x"), config.getDouble("spawn_loc.y"), config.getDouble("spawn_loc.z"),
                (float) config.getDouble("spawn_loc.yaw"), (float) config.getDouble("spawn_loc.pitch")));
        settings.setJoinKits(config.getStringList("join_kits"));
        settings.setWelcomeTitle(ChatColor.translateAlternateColorCodes('&', config.getString("welcome_title")));
        settings.setWelcomeSubTitle(ChatColor.translateAlternateColorCodes('&', config.getString("welcome_subtitle")));
        settings.setConfirmed(config.getBoolean("confirmed", false));
        settings.setQrCodeLores(config.getStringList("qr_code_lores"));

        List<String> bsBarMsgs = config.getStringList("bs_bar_msgs");

        settings.setBsBarMsgs(BukkitUtil.translateListColorCode(bsBarMsgs));
    }
}

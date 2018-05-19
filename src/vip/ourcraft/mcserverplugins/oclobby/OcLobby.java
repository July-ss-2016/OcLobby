package vip.ourcraft.mcserverplugins.oclobby;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import vip.creeper.mcserverplugins.creeperkits.CreeperKits;
import vip.creeper.mcserverplugins.creeperkits.Kit;
import vip.creeper.mcserverplugins.creeperkits.managers.KitManager;
import vip.ourcraft.mcserverplugins.oclobby.listeners.AuthMeListener;
import vip.ourcraft.mcserverplugins.oclobby.listeners.BukkitListener;
import vip.ourcraft.mcserverplugins.oclobby.listeners.QrCodeListener;
import vip.ourcraft.mcserverplugins.oclobby.tasks.BossBarUpdateTask;
import vip.ourcraft.mcserverplugins.oclobby.tasks.TimeLockTask;
import vip.ourcraft.mcserverplugins.oclobby.utils.BukkitUtil;

/**
 * Created by July on 2018/04/30.
 */
public class OcLobby extends JavaPlugin {
    private static OcLobby instance;
    private Settings settings;
    private boolean isCreeperKitsEnabled;
    private boolean isImageOnMapEnabled;
    private BossBarUpdateTask bossBarUpdateTask;

    public void onEnable() {
        instance = this;
        this.settings = new Settings();
        this.isCreeperKitsEnabled = Bukkit.getPluginManager().isPluginEnabled("CreeperKits");
        this.isImageOnMapEnabled = Bukkit.getPluginManager().isPluginEnabled("ImageOnMap");

        loadConfig();

        this.bossBarUpdateTask = new BossBarUpdateTask(this);

        if (Bukkit.getPluginManager().isPluginEnabled("AuthMe")) {
            Bukkit.getPluginManager().registerEvents(new AuthMeListener(this), this);
        }

        if (isImageOnMapEnabled && isCreeperKitsEnabled) {
            Bukkit.getPluginManager().registerEvents(new QrCodeListener(this), this);
        }

        Bukkit.getPluginManager().registerEvents(new BukkitListener(this), this);

        getCommand("oclobby").setExecutor(new AdminCommand(this));

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new TimeLockTask(this), 0L, 20L);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, bossBarUpdateTask, 0L, 40L);

        for (Player player : Bukkit.getOnlinePlayers()) {
            bossBarUpdateTask.getBar().addPlayer(player);
        }
    }

    public void onDisable() {
        bossBarUpdateTask.getBar().removeAll();
        Bukkit.getScheduler().cancelTasks(this);
    }

    public BossBarUpdateTask getBossBarUpdateTask() {
        return bossBarUpdateTask;
    }

    public static OcLobby getInstance() {
        return instance;
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
        settings.setWelcomeTitle(ChatColor.translateAlternateColorCodes('&', config.getString("welcome_title")));
        settings.setWelcomeSubTitle(ChatColor.translateAlternateColorCodes('&', config.getString("welcome_subtitle")));
        settings.setBsBarMsgs(BukkitUtil.translateListColorCode(config.getStringList("bs_bar_msgs")));

        if (isImageOnMapEnabled && isCreeperKitsEnabled) {
            KitManager kitManager = CreeperKits.getInstance().getKitManager();
            Kit qrCodeKit = kitManager.getKit(config.getString("qrcode_kit"));

            if (qrCodeKit == null) {
                getLogger().warning("qrcode_kit 不存在!");
                return;
            }

            settings.setQrCodeKit(kitManager.getKit(config.getString("qrcode_kit")));
        }
    }
}

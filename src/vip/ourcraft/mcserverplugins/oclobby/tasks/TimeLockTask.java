package vip.ourcraft.mcserverplugins.oclobby.tasks;

import org.bukkit.Bukkit;
import org.bukkit.World;
import vip.ourcraft.mcserverplugins.oclobby.OcLobby;
import vip.ourcraft.mcserverplugins.oclobby.Settings;

/**
 * Created by July on 2018/05/12.
 */
public class TimeLockTask implements Runnable {
    private Settings settings;

    public TimeLockTask(OcLobby plugin) {
        this.settings = plugin.getSettings();
    }

    @Override
    public void run() {
        if (settings.getLockTime() != -1) {
            for (World world : Bukkit.getWorlds()) {
                world.setTime(settings.getLockTime());
            }
        }
    }
}

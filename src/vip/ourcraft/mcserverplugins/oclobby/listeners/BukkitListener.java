package vip.ourcraft.mcserverplugins.oclobby.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import vip.creeper.mcserverplugins.creeperkits.CreeperKits;
import vip.ourcraft.mcserverplugins.oclobby.OcLobby;

/**
 * Created by July on 2018/04/30.
 */
public class BukkitListener implements Listener {
    private OcLobby plugin;

    public BukkitListener(OcLobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // 必须在同步线程中操作
        Bukkit.getScheduler().runTask(plugin, () -> {
            // 传送至指定出生点
            player.teleport(plugin.getSettings().getForceSpawnLoc());

            // 首次登录礼包
            if (!player.hasPlayedBefore() && plugin.getCreeperKitsEnabled()) {
                for (String kitName : plugin.getSettings().getFirstLoginKits()) {
                    CreeperKits.getInstance().getKitManager().getKit(kitName).give(player);
                }
            }
        });
    }
}

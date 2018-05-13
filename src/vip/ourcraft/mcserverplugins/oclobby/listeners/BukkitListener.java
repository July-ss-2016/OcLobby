package vip.ourcraft.mcserverplugins.oclobby.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import vip.creeper.mcserverplugins.creeperkits.CreeperKits;
import vip.ourcraft.mcserverplugins.oclobby.OcLobby;
import vip.ourcraft.mcserverplugins.oclobby.Settings;

/**
 * Created by July on 2018/04/30.
 */
public class BukkitListener implements Listener {
    private OcLobby plugin;
    private Settings settings;

    public BukkitListener(OcLobby plugin) {
        this.plugin = plugin;
        this.settings = plugin.getSettings();
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        if (!event.getPlayer().hasPermission("oclobby.admin")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        if (!event.getPlayer().hasPermission("oclobby.admin")) {
            event.setCancelled(true);
            event.setBuild(false);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // 必须在同步线程中操作
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            // 添加至bar
            plugin.getBossBarUpdateTask().addPlayer(player);
            // 传送至指定出生点
            player.teleport(settings.getSpawnLoc());

            if (settings.isConfirmed() && !player.hasPermission("oclobby.admin")) {
                player.getInventory().clear();
            }

            // 首次登录礼包
            if (plugin.getCreeperKitsEnabled()) {
                for (String kitName : settings.getJoinKits()) {
                    CreeperKits.getInstance().getKitManager().getKit(kitName).give(player);
                }
            }
        }, 5L);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        plugin.getBossBarUpdateTask().removePlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        // 必须在同步线程中操作
        Bukkit.getScheduler().runTask(plugin, () -> {
            // 传送至指定出生点
            player.teleport(settings.getSpawnLoc());
        });
    }

    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerPickupItemEvent(PlayerPickupItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        event.setCancelled(true);
    }
}

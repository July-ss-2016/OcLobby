package vip.ourcraft.mcserverplugins.oclobby.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import vip.ourcraft.mcserverplugins.oclobby.OcLobby;
import vip.ourcraft.mcserverplugins.oclobby.Settings;

/**
 * Created by July on 2018/04/30.
 * 禁止在登录服破坏|放置 方块、丢弃|拾取 物品、攻击实体
 * 登录服允许聊天，将设置严格的正则表达式
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

        event.setJoinMessage("§7[§a+§7] " + player.getName());
        // 必须在同步线程中操作
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            // 添加至bar
            plugin.getBossBarUpdateTask().getBar().addPlayer(player);
            // 传送至指定出生点
            player.teleport(settings.getSpawnLoc());
        }, 5L);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        plugin.getBossBarUpdateTask().getBar().addPlayer(player);
        event.setQuitMessage("§7[§c-§7] " + player.getName());

        if (settings.isQuitClearInv()) {
            player.getInventory().clear();
        }
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
        if (!event.getPlayer().hasPermission("oclobby.admin")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPickupItemEvent(EntityPickupItemEvent event) {
        Entity entity  = event.getEntity();

        if (!entity.hasPermission("oclobby.admin")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (!event.getEntity().hasPermission("oclobby.admin")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
/*        event.setFormat("[大厅][" + event.getPlayer().getName() + "] " + ChatColor.stripColor(event.getMessage()));*/
    }
}

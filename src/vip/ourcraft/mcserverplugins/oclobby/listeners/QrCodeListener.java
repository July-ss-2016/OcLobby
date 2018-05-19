package vip.ourcraft.mcserverplugins.oclobby.listeners;

import fr.moribus.imageonmap.image.MapInitEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import vip.creeper.mcserverplugins.creeperkits.Kit;
import vip.ourcraft.mcserverplugins.oclobby.OcLobby;
import vip.ourcraft.mcserverplugins.oclobby.Settings;

/**
 * Created by July on 2018/05/13.
 * 二维码的更好显示
 */
public class QrCodeListener implements Listener {
    private OcLobby plugin;
    private Settings settings;

    public QrCodeListener(OcLobby plugin) {
        this.plugin = plugin;
        this.settings = plugin.getSettings();
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerInventory playerInventory = player.getInventory();
        Kit kit = settings.getQrCodeKit();

        if (kit != null) {
            Bukkit.getScheduler().runTask(plugin, () -> {
                ItemStack item = kit.getItems().get(0);

                // 初始化地图
                MapInitEvent.initMap(item);
                playerInventory.setItemInOffHand(item);

            });
        }
    }

    @EventHandler
    public void onPlayerSwapHandItemsEvent(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();

        correctPitch(player, event.getMainHandItem());
    }

    @EventHandler
    public void onPlayerItemHeldEvent(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        correctPitch(player, player.getInventory().getItem(event.getNewSlot()));
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        correctPitch(player, player.getInventory().getItemInMainHand());
    }

    private boolean correctPitch(Player player, ItemStack item) {
        Kit kit = settings.getQrCodeKit();

        if (kit != null && kit.getItems().get(0).equals(item)) {
            Location newLoc = player.getLocation();

            newLoc.setPitch(80F);
            player.teleport(newLoc);
            return true;
        }

        return true;
    }
}

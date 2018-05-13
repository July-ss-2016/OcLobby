package vip.ourcraft.mcserverplugins.oclobby.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by July on 2018/05/13.
 */
public class QrCodeListener implements Listener {
    @EventHandler
    public void onPlayerItemHeldEvent(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

    }

    private boolean isHeldQrCodeItem(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
    }
}

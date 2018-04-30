package vip.ourcraft.mcserverplugins.oclobby.listeners;

import fr.xephi.authme.events.LoginEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by July on 2018/04/30.
 */
public class AuthMeListener implements Listener {
    @EventHandler
    public void onLoginEvent(LoginEvent event) {
        if (event.isLogin()) {
            Player player = event.getPlayer();
            Location playerLoc = player.getLocation();

            // 天空一声巨响，劳资闪亮登场！
            playerLoc.getWorld().strikeLightningEffect(playerLoc);
        }
    }
}

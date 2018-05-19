package vip.ourcraft.mcserverplugins.oclobby.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import fr.xephi.authme.events.LoginEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import vip.ourcraft.mcserverplugins.oclobby.OcLobby;
import vip.ourcraft.mcserverplugins.oclobby.Settings;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by July on 2018/04/30.
 * 登录服霹雳效果
 */
public class AuthMeListener implements Listener {
    private Settings settings;

    public AuthMeListener(OcLobby plugin) {
        this.settings = plugin.getSettings();
    }

    @EventHandler
    public void onLoginEvent(LoginEvent event) {
        if (event.isLogin()) {
            Player player = event.getPlayer();
            Location playerLoc = player.getLocation();

            for (int i = 0; i < 3; i ++) {
                // 天空一声巨响，劳资闪亮登场！
                playerLoc.getWorld().strikeLightningEffect(playerLoc);
            }

            PacketContainer titlePacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.TITLE);
            PacketContainer subTitlePacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.TITLE);

            titlePacket.getIntegers().write(0, 10);
            titlePacket.getIntegers().write(1, 60);
            titlePacket.getIntegers().write(2, 10);
            titlePacket.getChatComponents().write(0, WrappedChatComponent.fromJson(settings.getWelcomeTitle()));


            subTitlePacket.getIntegers().write(0, 10);
            subTitlePacket.getIntegers().write(1, 60);
            subTitlePacket.getIntegers().write(2, 10);
            subTitlePacket.getChatComponents().write(0, WrappedChatComponent.fromJson(settings.getWelcomeSubTitle()));
            subTitlePacket.getTitleActions().write(0, EnumWrappers.TitleAction.SUBTITLE);

            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, titlePacket);
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, subTitlePacket);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}

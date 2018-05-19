package vip.ourcraft.mcserverplugins.oclobby.tasks;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import vip.ourcraft.mcserverplugins.oclobby.OcLobby;

import java.util.List;

/**
 * Created by July on 2018/05/12.
 */
public class BossBarUpdateTask implements Runnable {
    private OcLobby plugin;
    private BossBar bar;
    private int barMsgCounter;

    public BossBarUpdateTask(OcLobby plugin) {
        this.barMsgCounter = 1;
        this.plugin = plugin;
        this.bar = Bukkit.createBossBar("", BarColor.BLUE, BarStyle.SEGMENTED_20);

        bar.setVisible(true);
    }

    @Override
    public void run() {
        Bukkit.getScheduler().runTask(plugin, () -> {
            List<String> barMsgs = plugin.getSettings().getBsBarMsgs();
            int barMsgsSize = barMsgs.size();

            bar.setProgress((double) barMsgCounter / barMsgsSize);
            bar.setTitle(barMsgs.get(barMsgCounter - 1));

            if (barMsgCounter >= barMsgsSize) {
                barMsgCounter = 0;
            }

            barMsgCounter ++;
        });
    }

    public BossBar getBar() {
        return bar;
    }
}

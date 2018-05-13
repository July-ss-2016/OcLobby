package vip.ourcraft.mcserverplugins.oclobby.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by July on 2018/05/12.
 */
public class BukkitUtil {
    public static List<String> translateListColorCode(List<String> list) {
        if (list == null) {
            return null;
        }

        List<String> result = new ArrayList<>();

        for (String s : list) {
            result.add(ChatColor.translateAlternateColorCodes('&', s));
        }

        return result;
    }
}

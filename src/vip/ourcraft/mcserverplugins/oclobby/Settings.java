package vip.ourcraft.mcserverplugins.oclobby;

import org.bukkit.Location;

import java.util.List;

/**
 * Created by July on 2018/04/30.
 */
public class Settings {
    private boolean isDisplayLoginInEffect;
    private Location forceSpawnLoc;
    private List<String> firstLoginKits;

    public boolean isDisplayLoginInEffect() {
        return isDisplayLoginInEffect;
    }

    public void setDisplayLoginInEffect(boolean displayLoginInEffect) {
        isDisplayLoginInEffect = displayLoginInEffect;
    }

    public Location getForceSpawnLoc() {
        return forceSpawnLoc;
    }

    public void setForceSpawnLoc(Location forceSpawnLoc) {
        this.forceSpawnLoc = forceSpawnLoc;
    }

    public List<String> getFirstLoginKits() {
        return firstLoginKits;
    }

    public void setFirstLoginKits(List<String> firstLoginKits) {
        this.firstLoginKits = firstLoginKits;
    }
}

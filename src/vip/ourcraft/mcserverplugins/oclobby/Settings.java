package vip.ourcraft.mcserverplugins.oclobby;

import org.bukkit.Location;
import vip.creeper.mcserverplugins.creeperkits.Kit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by July on 2018/04/30.
 */
public class Settings {
    private boolean isDisplayLoginInEffect;
    private Location spawnLoc;
    private int lockTime;
    private String welcomeTitle;
    private String welcomeSubTitle;
    private List<String> bsBarMsgs;
    private Kit qrCodeKit;

    public Kit getQrCodeKit() {
        return qrCodeKit;
    }

    public void setQrCodeKit(Kit qrCodeKit) {
        this.qrCodeKit = qrCodeKit;
    }

    public List<String> getBsBarMsgs() {
        return bsBarMsgs;
    }

    public void setBsBarMsgs(List<String> bsBarMsgs) {
        this.bsBarMsgs = bsBarMsgs;
    }

    public String getWelcomeSubTitle() {
        return welcomeSubTitle;
    }

    public void setWelcomeSubTitle(String welcomeSubTitle) {
        this.welcomeSubTitle = welcomeSubTitle;
    }

    public String getWelcomeTitle() {
        return welcomeTitle;
    }

    public void setWelcomeTitle(String welcomeTitle) {
        this.welcomeTitle = welcomeTitle;
    }

    public int getLockTime() {
        return lockTime;
    }

    public void setLockTime(int lockTime) {
        this.lockTime = lockTime;
    }

    public boolean isDisplayLoginInEffect() {
        return isDisplayLoginInEffect;
    }

    public void setDisplayLoginInEffect(boolean displayLoginInEffect) {
        isDisplayLoginInEffect = displayLoginInEffect;
    }

    public Location getSpawnLoc() {
        return spawnLoc;
    }

    public void setSpawnLoc(Location spawnLoc) {
        this.spawnLoc = spawnLoc;
    }
}

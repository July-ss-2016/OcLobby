package vip.ourcraft.mcserverplugins.oclobby;

import org.bukkit.Location;

import java.util.List;

/**
 * Created by July on 2018/04/30.
 */
public class Settings {
    private boolean confirmed;
    private boolean isDisplayLoginInEffect;
    private Location spawnLoc;
    private List<String> joinKits;
    private int lockTime;
    private String welcomeTitle;
    private String welcomeSubTitle;
    private List<String> bsBarMsgs;
    private List<String> qrCodeLores;

    public List<String> getQrCodeLores() {
        return qrCodeLores;
    }

    public void setQrCodeLores(List<String> qrCodeLores) {
        this.qrCodeLores = qrCodeLores;
    }

    public List<String> getBsBarMsgs() {
        return bsBarMsgs;
    }

    public void setBsBarMsgs(List<String> bsBarMsgs) {
        this.bsBarMsgs = bsBarMsgs;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
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

    public List<String> getJoinKits() {
        return joinKits;
    }

    public void setJoinKits(List<String> joinKits) {
        this.joinKits = joinKits;
    }
}

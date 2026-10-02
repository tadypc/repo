package power_monitor;

import java.time.Duration;
import java.time.LocalDateTime;

public class Device {
    private Long  id;
    private String name;
    private String address;
    private boolean online;
    private static long idCounter = 1;
    private String host;
    private LocalDateTime lastPingTime;
    private LocalDateTime stateChangeTime = LocalDateTime.now(); // момент останньої зміни статусу

    public Device() {
        id = idCounter++;
    }
    public Device(String name, String address) {
        this.id = idCounter++;
        this.name = name;
        this.address = address;
        this.online = false;
    }
    public Device(String name, String address, String host) {
        this.id = idCounter++;
        this.name = name;
        this.address = address;
        this.online = false;
        this.host = host;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public boolean isOnline() {
        return online;
    }
    public void setOnline(boolean online) {
        this.online = online;
    }
    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }
    public LocalDateTime getLastPingTime() { return lastPingTime; }
    public void setLastPingTime(LocalDateTime lastPingTime) {
        this.lastPingTime = lastPingTime;
    }
    public LocalDateTime getStateChangeTime() {
        return stateChangeTime;
    }
    public void setStateChangeTime(LocalDateTime stateChangeTime) {
        this.stateChangeTime = stateChangeTime;
    }

    public String getFormattedDuration() {
        if (stateChangeTime == null) return "невідомо";
        Duration duration = Duration.between(stateChangeTime, java.time.LocalDateTime.now());
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        if (hours > 0) {
            return hours + " год. " + minutes + " хв.";
        } else {
            return minutes + " хв.";
        }
    }
}

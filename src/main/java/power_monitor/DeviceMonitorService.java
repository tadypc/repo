package power_monitor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class DeviceMonitorService {

    private final DeviceService deviceService;
    private final TelegramBotService telegramBotService;

    public DeviceMonitorService(DeviceService deviceService, TelegramBotService telegramBotService) {
        this.deviceService = deviceService;
        this.telegramBotService = telegramBotService;
    }

    @Scheduled(fixedRate = 60000)
    public void monitorDevices() {
        for (Device device : deviceService.getDevices()) {
            boolean previousStatus = device.isOnline();
            boolean currentStatus;

            if (device.getHost() == null || device.getHost().isEmpty()) {
                currentStatus = checkHeartbeatStatus(device);
            } else {
                currentStatus = checkNetworkPing(device.getHost());
            }

            if (currentStatus) {
                device.setLastPingTime(LocalDateTime.now());
            }

            if (previousStatus != currentStatus) {
                String durationStr = device.getFormattedDuration();

                device.setOnline(currentStatus);
                device.setStateChangeTime(LocalDateTime.now());

                if (telegramBotService.getChatId() != null) {
                    String statusText;
                    String durationLabel;

                    if (currentStatus) {
                        statusText = "🟢 Світло з'явилось! Пристрій ONLINE.";
                        durationLabel = "⏳ Світла не було: ";
                    } else {
                        statusText = "🔴 Світло зникло! Пристрій OFFLINE.";
                        durationLabel = "⏳ Світло було: ";
                    }

                    String message = "⚠️ Зміна статусу!\n" +
                            "• Пристрій: " + device.getName() + " (" + device.getAddress() + ")\n" +
                            statusText + "\n" +
                            durationLabel + durationStr;

                    telegramBotService.sendMessage(telegramBotService.getChatId(), message);
                }
            }
        }
    }

    private boolean checkNetworkPing(String host) {
        try {
            Process process = new ProcessBuilder("ping", "-c", "1", "-W", "3", host).start();
            return process.waitFor() == 0;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkHeartbeatStatus(Device device) {
        if (device.getLastPingTime() == null) {
            return false;
        }
        long secondsSinceLastPing = ChronoUnit.SECONDS.between(device.getLastPingTime(), LocalDateTime.now());
        return secondsSinceLastPing <= 180;
    }
}
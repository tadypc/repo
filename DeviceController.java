package power_monitor;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class DeviceController {
    private final DeviceService deviceService;
    private final TelegramBotService telegramBotService;

    public DeviceController(DeviceService deviceService, TelegramBotService telegramBotService) {
        this.deviceService = deviceService;
        this.telegramBotService = telegramBotService;
    }

    @GetMapping("/devices")
    public ArrayList<Device> getDevices() {
        return deviceService.getDevices();
    }

    @PostMapping("/devices")
    public Device addDevice(@RequestBody Device device) {
        deviceService.addDevice(device);
        return device;
    }

    @GetMapping("/devices/{id}")
    public Device findById(@PathVariable long id) {
        return deviceService.findById(id);
    }

    @PostMapping("/devices/{id}/heartbeat")
    public String heartbeatDevice(@PathVariable long id) {
        Device device = deviceService.findById(id);
        if (device != null) {
            boolean wasOnline = device.isOnline();
            if (!wasOnline) {
                String durationStr = device.getFormattedDuration();
                device.setOnline(true);
                device.setLastPingTime(java.time.LocalDateTime.now());
                device.setStateChangeTime(java.time.LocalDateTime.now());
                if (telegramBotService.getChatId() != null) {
                    String message = "⚠️ Зміна статусу!\n" +
                            "• Пристрій: " + device.getName() + " (" + device.getAddress() + ")\n" +
                            "🟢 Світло є! Пристрій ONLINE.\n" +
                            "⏳ Світла не було: " + durationStr;
                    telegramBotService.sendMessage(telegramBotService.getChatId(), message);
                }
            } else {
                device.setLastPingTime(java.time.LocalDateTime.now());
            }
            return "Heartbeat received for device: " + device.getName();
        }
        return "Device with id: " + id + " not found";
    }
}
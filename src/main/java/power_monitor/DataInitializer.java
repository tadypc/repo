package power_monitor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataInitializer implements CommandLineRunner {
    private final DeviceService deviceService;

    public DataInitializer(DeviceService deviceService) {
        this.deviceService = deviceService;
    }
    @Override
    public void run(String... args) throws Exception {
        deviceService.addDevice(new Device("Wifi Rozetka", "Akhmatovoi", "192.168.0.110"));
        deviceService.addDevice(new Device("ESP32", "Kyrylo-Mefod"));
        deviceService.addDevice(new Device("IP", "Sanya", "176.100.8.24"));
    }
}

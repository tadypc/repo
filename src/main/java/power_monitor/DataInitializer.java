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
        deviceService.addDevice(new Device("Модуль ESP32-C3", "Дім"));
        deviceService.addDevice(new Device("Модуль ESP32-SIM800L", "Гуртожиток"));
        deviceService.addDevice(new Device("IP", "Дім Сані", "176.100.8.24"));
    }
}

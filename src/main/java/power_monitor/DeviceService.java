package power_monitor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DeviceService {
    private ArrayList<Device> devices = new ArrayList<>();

    public void addDevice(Device device) {
        devices.add(device);
    }

    public ArrayList<Device> getDevices() {
        return devices;
    }

    public Device findById(long id) {
        for (Device device : devices) {
            if (device.getId().equals(id)) {
                return device;
            }
        }
        return null;
    }

}

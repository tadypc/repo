package power_monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PowerMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PowerMonitorApplication.class, args);
	}

}

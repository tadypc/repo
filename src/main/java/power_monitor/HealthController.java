package power_monitor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @RequestMapping(value = "/health", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }
}

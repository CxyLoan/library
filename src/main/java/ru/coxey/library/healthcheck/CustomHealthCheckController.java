package ru.coxey.library.healthcheck;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.coxey.library.dto.HealthStatus;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customhealth/")
public class CustomHealthCheckController {

    private final CustomHealthCheckService service;

    @GetMapping("/database")
    public ResponseEntity<HealthStatus> health() {
        try {
            service.healthCheck();
            return ResponseEntity.ok(new HealthStatus("UP"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new HealthStatus("DOWN"));
        }
    }

    @GetMapping("/anotherservice")
    public ResponseEntity<HealthStatus> healthAnotherService() {
        try {
            service.healthAnotherService();
            return ResponseEntity.ok(new HealthStatus("UP"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new HealthStatus("DOWN"));
        }
    }
}

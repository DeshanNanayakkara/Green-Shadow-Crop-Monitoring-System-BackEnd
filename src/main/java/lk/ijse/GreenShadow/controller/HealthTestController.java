package lk.ijse.GreenShadow.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/healthTest")
public class HealthTestController {
    @GetMapping
    public String healthCheck() {
        return "Crop Monitoring System is running";
    }
}

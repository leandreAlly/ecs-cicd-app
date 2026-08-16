package com.leandre.ecscicd;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    private final String version;
    private final String commit;
    private final String builtAt;

    public AppController(
            @Value("${app.version:local}") String version,
            @Value("${app.commit:unknown}") String commit,
            @Value("${app.builtAt:unknown}") String builtAt) {
        this.version = version;
        this.commit = commit;
        this.builtAt = builtAt;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok", "commit", commit);
    }

    @GetMapping("/api/version")
    public Map<String, String> version() {
        return Map.of(
                "version", version,
                "commit", commit,
                "builtAt", builtAt);
    }
}

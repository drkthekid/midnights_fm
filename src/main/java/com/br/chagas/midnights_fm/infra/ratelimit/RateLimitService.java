package com.br.chagas.midnights_fm.infra.ratelimit;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {

    // guarda attempts for ip
    private final Map<String, LoginAttempt> attemps = new ConcurrentHashMap<>();

    // const
    private static final int MAX_ATTEMPTS = 5;
    private static final int BLOCK_MINUTES = 5;

    public boolean isBlocked(String ip) {
        LoginAttempt attempt = attemps.get(ip);

        if (attempt == null) return false;

        // if blocked
        if (attempt.getBlockedUntil() != null &&
                attempt.getBlockedUntil().isAfter(LocalDateTime.now())) {
            return true;
        }

        return false;
    }

    public void loginFailed(String ip) {
        LoginAttempt attempt = attemps.getOrDefault(
                ip,
                new LoginAttempt(0, LocalDateTime.now(), null)
        );

        attempt.setCount(attempt.getCount() + 1);
        attempt.setBlockedUntil(LocalDateTime.now());

        if (attempt.getCount() >= MAX_ATTEMPTS) {
            attempt.setBlockedUntil(LocalDateTime.now().plusMinutes(BLOCK_MINUTES));
        }

        attemps.put(ip, attempt);
    }

    public void loginSuccess(String ip) {
        // reset attempts
        attemps.remove(ip);
    }
}

package com.rocky.controller.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    // key = client (IP), value = window state
    private final Map<String, Window> clientWindows = new ConcurrentHashMap<>();

    // Default: 20 requests per 60 seconds per client
    private static final int DEFAULT_LIMIT = 20;
    private static final long DEFAULT_WINDOW_SECONDS = 60;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();

        // allow actuator always
        if (uri.startsWith("/actuator")) return true;

        String clientKey = getClientKey(request);

        Limit limit = resolveLimit(uri);

        boolean allowed = allowRequest(clientKey, limit.maxRequests, limit.windowSeconds);

        if (allowed) return true;

        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value()); // 429
        response.setContentType("text/plain");
        response.setHeader("Retry-After", "60");
        response.getWriter().write("Too many requests (429). Please try again later.");
        return false;
    }

    private boolean allowRequest(String clientKey, int maxRequests, long windowSeconds) {
        long now = Instant.now().getEpochSecond();

        Window window = clientWindows.compute(clientKey, (k, w) -> {
            if (w == null) {
                return new Window(now, 1);
            }
            // new window?
            if (now - w.windowStartEpochSec >= windowSeconds) {
                w.windowStartEpochSec = now;
                w.count = 1;
                return w;
            }
            // same window
            w.count++;
            return w;
        });

        return window.count <= maxRequests;
    }

    private Limit resolveLimit(String uri) {
        // Tune per endpoint (change as you want)
        if (uri.endsWith("/defaultMessage")) {
            return new Limit(30, 60); // 30/min
        }
        if (uri.contains("/getAllCars")) {
            return new Limit(10, 60); // 10/min (DB heavy)
        }
        return new Limit(DEFAULT_LIMIT, DEFAULT_WINDOW_SECONDS);
    }

    private String getClientKey(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) {
            return xff.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    private static class Window {
        long windowStartEpochSec;
        int count;

        Window(long windowStartEpochSec, int count) {
            this.windowStartEpochSec = windowStartEpochSec;
            this.count = count;
        }
    }

    private static class Limit {
        int maxRequests;
        long windowSeconds;

        Limit(int maxRequests, long windowSeconds) {
            this.maxRequests = maxRequests;
            this.windowSeconds = windowSeconds;
        }
    }
}

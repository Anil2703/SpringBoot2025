package com.rocky.controller.interceptor;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitInterceptorWithBucket4J implements HandlerInterceptor {

    // One bucket per client + endpoint
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private static final int DEFAULT_LIMIT = 20;
    private static final int DEFAULT_WINDOW_SECONDS = 60;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();

        // allow actuator always
        if (uri.startsWith("/actuator")) {
            return true;
        }

        String clientIp = getClientIp(request);

        // If you want per-IP-per-endpoint limiting:
        String key = clientIp + ":" + uri;

        Bucket bucket = buckets.computeIfAbsent(key, k -> createBucketForUri(uri));

        if (bucket.tryConsume(1)) {
            return true;
        }

        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value()); // 429
        response.setContentType("text/plain");
        response.getWriter().write("Too many requests (429). Please try again later.");
        return false;
    }

    private Bucket createBucketForUri(String uri) {

        // Tune per endpoint (same logic you already had)
        if (uri.contains("/defaultMessage")) {
            return newBucket(30, 60); // 30/min
        }
        if (uri.contains("/getAllCars")) {
            return newBucket(10, 60); // 10/min (DB heavy)
        }

        return newBucket(DEFAULT_LIMIT, DEFAULT_WINDOW_SECONDS);
    }

    private Bucket newBucket(int maxRequests, int windowSeconds) {
        Bandwidth limit = Bandwidth.classic(
                maxRequests,
                Refill.intervally(maxRequests, Duration.ofSeconds(windowSeconds))
        );
        return Bucket.builder().addLimit(limit).build();
    }

    private String getClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) {
            return xff.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}

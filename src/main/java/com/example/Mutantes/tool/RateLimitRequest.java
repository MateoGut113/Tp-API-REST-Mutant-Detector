package com.example.Mutantes.tool;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitRequest extends OncePerRequestFilter {

    private static final int MAX_REQUESTS = 10;
    private static final long WINDOW_MILLIS = 60_000; // 1 minuto

    private final Map<String, RequestCounter> requestCounts = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/h2-console")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (request.getRequestURI().equals("/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String ip = request.getRemoteAddr();
        long now = System.currentTimeMillis();

        RequestCounter counter = requestCounts.computeIfAbsent(ip, k -> new RequestCounter());

        synchronized (counter) {
            if (now - counter.startTime > WINDOW_MILLIS) {
                // Reiniciar ventana
                counter.startTime = now;
                counter.count = 0;
            }

            counter.count++;

            if (counter.count > MAX_REQUESTS) {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value()); // 429
                response.getWriter().write("Rate limit exceeded: Máx 10 requests/min por IP");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private static class RequestCounter {
        long startTime = System.currentTimeMillis();
        int count = 0;
    }
}



package dev.gunho.toooy.global.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            // 요청을 ContentCachingRequestWrapper로 래핑한다.
            ContentCachingRequestWrapper cachingRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);


            // 체인 진행 (요청 처리가 먼저 이루어질 수 있도록)
            chain.doFilter(cachingRequest, response);

            // 요청 본문을 로깅
            logRequest(cachingRequest);
        } else {
            chain.doFilter(request, response);
        }
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        // 요청 정보를 로깅
        log.info("=== Request Content Logging ===");
        log.info("Method: {}", request.getMethod());
        log.info("URI: {}", request.getRequestURI());

        // Body 로깅
        try {
            byte[] content = request.getContentAsByteArray();
            if (content.length > 0) {
                String body = new String(content, StandardCharsets.UTF_8);
                log.info("Body: {}", body);
            } else {
                log.info("No Body Content");
            }
        } catch (Exception e) {
            log.error("LogFilter.logRequest Exception: {}", e.getMessage()) ;
        }

    }
}

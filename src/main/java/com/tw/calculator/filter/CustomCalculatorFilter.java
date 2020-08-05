package com.tw.calculator.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Service
@Slf4j
public class CustomCalculatorFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
//        log.info(
//                "Starting a transaction for req : {} {}",
//                req.getRequestURI(), req.getMethod());
//
//        chain.doFilter(request, response);
//        log.info(
//                "Committing a transaction for req : {} {}",
//                req.getRequestURI(),res.getHeaders("Date"));

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(req);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(res);

        try {
            chain.doFilter(requestWrapper, responseWrapper);
        } finally {
            String request1 = new String(requestWrapper.getContentAsByteArray());
            String response1 = new String(responseWrapper.getContentAsByteArray());

            Object calculatorRequest;
            Object calculatorResponse;
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                calculatorRequest = objectMapper.readValue(request1, Object.class);
                calculatorResponse = objectMapper.readValue(response1, Object.class);

            } catch (Exception ex) {
                calculatorRequest = request1;
                calculatorResponse = response1;
            }

            MDC.put("requestBody", request1);
            MDC.put("responseBody", response1);
            log.info("calculator request======>", kv("requestBody", calculatorRequest));
            log.info("calculator response=====>", kv("responseBody", calculatorResponse));

            responseWrapper.copyBodyToResponse();
        }
    }
}

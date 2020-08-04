package com.tw.calculator.filter;

import org.springframework.stereotype.Service;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Service
public class CommonsOrderedRequestLoggingFilter extends CommonsRequestLoggingFilter {

    CommonsOrderedRequestLoggingFilter() {
        super();
        this.setIncludeQueryString(true);
        this.setIncludeHeaders(true);
        this.setIncludePayload(true);
        this.setIncludeClientInfo(true);
        this.setMaxPayloadLength(1000);
        this.setAfterMessagePrefix("CALCULATION : ");
    }
}
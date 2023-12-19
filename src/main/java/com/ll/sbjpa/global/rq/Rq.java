package com.ll.sbjpa.global.rq;

import com.ll.sbjpa.global.standard.util.Ut;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Component
@RequiredArgsConstructor
public class Rq {
    private final HttpServletResponse resp;
    private final HttpServletRequest req;

    public String getCurrentQueryStringWithoutParam(String paramName) {
        String queryString = req.getQueryString();

        if(queryString == null) {
            return "";
        }

        queryString = Ut.url.deleteQueryParam(queryString, paramName);

        return queryString;
    }
}

package com.playground.htmx;

import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
@RequiredArgsConstructor
public class MultiViewsResolver implements ViewResolver {

    private final ViewResolver viewResolver;

    @Override
    public View resolveViewName(String viewNames, Locale locale) throws Exception {
        List<View> views = new ArrayList<>();
        for (String viewName : viewNames.split(",")) {
            views.add(viewResolver.resolveViewName(viewName.trim(), locale));
        }
        if (views.isEmpty()) {
            return null;
        }

        return (model, request, response) -> {
            ContentCachingResponseWrapper wrapper = new ContentCachingResponseWrapper(response);
            for (View view : views) {
                view.render(model, request, wrapper);
            }
            wrapper.copyBodyToResponse();
        };
    }
}

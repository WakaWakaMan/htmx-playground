package com.playground.htmx.toasts;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Collections;

@Controller
@RequestMapping("/toasts")
public class ToastsController {

    @GetMapping
    public String page(Model model) {
        model.addAttribute("messages", Collections.singleton("Page toast"));
        model.addAttribute("model", model);
        return "toasts";
    }

    @PostMapping("/show-toast")
    public String showToast(Model model) {
        model.addAttribute("messages", Collections.singleton("'::' toast "));
        return "toasts :: toasts";
    }

    @PostMapping("/show-toasts")
    public String showToasts(Model model) {
        model.addAttribute("messages", Arrays.asList("'::' toast 1", "'::' toast 2"));
        return "toasts :: toasts";
    }

    @PostMapping("/show-empty-toast")
    public String showEmpty() {
        return "toasts :: toasts";
    }

    @PostMapping("/multi")
    public HtmxResponse showAll(Model model) {
        model.addAttribute("messages", Collections.singleton("HtmxResponse toast "));
        model.addAttribute("model", model.asMap());
        return HtmxResponse.builder()
                .view("toasts :: content")
                .view("toasts :: toasts")
                .build();
    }

    @PostMapping(value = "/multi-with-str")
    public String showAllStr(Model model) {
        model.addAttribute("messages", Collections.singleton("'::,::' toast "));
        model.addAttribute("model", model);
        return "toasts :: content, toasts :: toasts";
    }
}

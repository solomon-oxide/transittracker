package com.phoenixai.transittracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for serving the main application pages
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/map";
    }

    @GetMapping("/map")
    public String map() {
        return "map";
    }
}

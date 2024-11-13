package com.nordclan.nikgapon.work_practice_1.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainPageController {
    @RequestMapping("/")
    public String mainpage(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name", username);
        return "mainpage";
    }
}

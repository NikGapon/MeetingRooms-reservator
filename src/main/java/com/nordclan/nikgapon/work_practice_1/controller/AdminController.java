package com.nordclan.nikgapon.work_practice_1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/admin/rooms")
    public String Rooms(){
        return "rooms";
    }
}

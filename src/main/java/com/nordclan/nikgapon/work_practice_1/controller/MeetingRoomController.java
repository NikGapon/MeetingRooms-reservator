package com.nordclan.nikgapon.work_practice_1.controller;

import com.nordclan.nikgapon.work_practice_1.service.MeetingRoomService;
import com.nordclan.nikgapon.work_practice_1.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/admin/rooms")
public class MeetingRoomController {
    private final MeetingRoomService meetingRoomService;
    private final UserService userService;

    public MeetingRoomController(MeetingRoomService meetingRoomService, UserService userService) {
        this.meetingRoomService = meetingRoomService;
        this.userService = userService;
    }

    @GetMapping()
    public String getRooms(Model model){
        model.addAttribute("rooms", meetingRoomService.findAllRooms()
                .stream()
                .map(MeetingRoomDto::new)
                .toList());
        return "rooms";
    }
    @GetMapping(value = {"/update", "/update/{id}"})
    public String Update(){
        return "update-room";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id, Principal principal) {
        // Удалил админ чеки, spring security может сам убрать запросы
            meetingRoomService.deleteRoom(id);

        return "redirect:/admin/rooms";
    }

}

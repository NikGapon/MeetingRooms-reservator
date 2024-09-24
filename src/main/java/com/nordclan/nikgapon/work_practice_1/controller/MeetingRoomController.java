package com.nordclan.nikgapon.work_practice_1.controller;

import com.nordclan.nikgapon.work_practice_1.service.MeetingRoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/rooms")
public class MeetingRoomController {
    private final MeetingRoomService meetingRoomService;

    public MeetingRoomController(MeetingRoomService meetingRoomService) {
        this.meetingRoomService = meetingRoomService;
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
}

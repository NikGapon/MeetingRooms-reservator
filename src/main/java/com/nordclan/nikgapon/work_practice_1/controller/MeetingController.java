package com.nordclan.nikgapon.work_practice_1.controller;

import com.nordclan.nikgapon.work_practice_1.service.MeetingRoomService;
import com.nordclan.nikgapon.work_practice_1.service.MeetingService;
import com.nordclan.nikgapon.work_practice_1.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/meeting")
public class MeetingController {
    private final MeetingService meetingService;
    private final UserService userService;

    public MeetingController(MeetingService meetingService, UserService userService) {
        this.meetingService = meetingService;
        this.userService = userService;
    }
    @GetMapping(value = {"/", "/{id}"})
    public String updateRoom(@PathVariable(required = false) Long id,
                             Model model, Principal principal){
        if (id == null || id <= 0) {
            model.addAttribute("meeting", new MeetingDto());
        } else {
            model.addAttribute("meeting", new MeetingDto(meetingService.findMeeting(id)));
            if (meetingService.findMeeting(id).getCreator() != userService.findByLogin(principal.getName())){
                return "meeting";
            }
        }
        return "meeting-update";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        meetingService.deleteMeeting(id);

        return "redirect:/week/";
    }
}

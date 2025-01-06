package com.nikgapon.MeetingRoomsReservator.controller;


import com.nikgapon.MeetingRoomsReservator.service.MeetingService;
import com.nikgapon.MeetingRoomsReservator.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/invitations")
public class InvitationsController {
    private final MeetingService meetingService;
    private final UserService userService;

    public InvitationsController(MeetingService meetingService, UserService userService) {
        this.meetingService = meetingService;
        this.userService = userService;
    }

    @GetMapping(value = {"/", ""})
    public String invitations(Model model, Principal principal) {
        model.addAttribute("meetings", meetingService.findAllInvitations(userService.findByLogin(principal.getName()).getId()));
        return "invitations";
    }
}

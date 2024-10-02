package com.nordclan.nikgapon.work_practice_1.controller;

import com.nordclan.nikgapon.work_practice_1.service.MeetingRoomService;
import com.nordclan.nikgapon.work_practice_1.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
@RequestMapping("/admin/rooms")
public class MeetingRoomController {
    private final MeetingRoomService meetingRoomService;
    private final UserService userService;

    public MeetingRoomController(MeetingRoomService meetingRoomService, UserService userService) {
        this.meetingRoomService = meetingRoomService;
        this.userService = userService;
    }

    @GetMapping(value = {"/", ""})
    public String getRooms(Model model){
        model.addAttribute("rooms", meetingRoomService.findAllRooms()
                .stream()
                .map(MeetingRoomDto::new)
                .toList());
        return "rooms";
    }
    @GetMapping(value = {"/room-update", "/room-update/{id}"})
    public String updateRoom(@PathVariable(required = false) Long id,
                         Model model){
        if (id == null || id <= 0) {
            model.addAttribute("room", new MeetingRoomDto());
        } else {

            model.addAttribute("room", new MeetingRoomDto(meetingRoomService.findRoom(id)));
        }
        return "room-update";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
            meetingRoomService.deleteRoom(id);

        return "redirect:/admin/rooms";
    }

    @PostMapping(value = {"/", "/{id}"})
    public String saveRoom(@PathVariable(required = false) Long id,

                           @ModelAttribute("room") MeetingRoomDto roomDto,
                           BindingResult bindingResult,
                           Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors",
                    bindingResult.getAllErrors());
            return "room-update";
        }
        if (id == null || id <= 0) {
            meetingRoomService.addRoom(roomDto);
            return "redirect:/admin/rooms";
        } else {
            meetingRoomService.updateRoom(id, roomDto);
        }
        return "redirect:/admin/rooms";
    }
}

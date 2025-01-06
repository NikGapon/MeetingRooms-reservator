package com.nikgapon.MeetingRoomsReservator.controller;

import com.nikgapon.MeetingRoomsReservator.dto.MeetingDto;
import com.nikgapon.MeetingRoomsReservator.service.MeetingRoomService;
import com.nikgapon.MeetingRoomsReservator.service.MeetingService;
import com.nikgapon.MeetingRoomsReservator.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/meeting")
public class MeetingController {
    private final MeetingService meetingService;
    private final UserService userService;
    private final MeetingRoomService meetingRoomService;

    public MeetingController(MeetingService meetingService, UserService userService, MeetingRoomService meetingRoomService) {
        this.meetingService = meetingService;
        this.userService = userService;
        this.meetingRoomService = meetingRoomService;
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
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("rooms", meetingRoomService.findAllRooms());

        return "meeting-update";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        meetingService.deleteMeeting(id);

        return "redirect:/week/";
    }

    @PostMapping(value = {"/", "/{id}"})
    public String saveMeeting(@PathVariable(required = false) Long id,
                           @ModelAttribute("meeting") MeetingDto meetingDto,
                           BindingResult bindingResult,
                           Model model, Principal principal) throws IOException {

        meetingDto.setCreator(userService.findByLogin(principal.getName()));
        if (meetingDto.getStarttime().after(meetingDto.getEndtime())) {
            bindingResult.rejectValue("endtime", "error.meeting", "Время начала должно быть позже времени окончания.");
        }
        else {
            long durationInMillis = meetingDto.getEndtime().getTime() - meetingDto.getStarttime().getTime();
            long durationInMinutes = durationInMillis / (60 * 1000);
            if (durationInMinutes < 30) {
                bindingResult.rejectValue("endtime", "error.meeting", "Встреча должна длиться минимум 30 минут.");
            }
            if (durationInMinutes > 24 * 60) {
                bindingResult.rejectValue("endtime", "error.meeting", "Встреча не может длиться более 24 часов.");
            }
        }
        if (meetingDto.getRoom() == null){
            bindingResult.rejectValue("room", "error.meeting", "Необходимо выбрать переговорку");
        }
        if (meetingDto.getTitle() == null){
            bindingResult.rejectValue("title", "error.meeting", "Название не должно быть пустым");
        }
        else{
            if (meetingDto.getTitle().startsWith(" ")) {
                bindingResult.rejectValue("title", "error.meeting", "Название не должно начинаться с пробела.");
            }}


        boolean roomOccupied = meetingService.isRoomOccupied(meetingDto.getRoom(), meetingDto.getStarttime(), meetingDto.getEndtime(), id == null ? -1L : id);
        if (roomOccupied ) {
            bindingResult.rejectValue("room", "error.meeting", "Комната занята в указанное время.");
        }

         if (bindingResult.hasErrors()) {
            model.addAttribute("errors",
                    bindingResult.getAllErrors());
            model.addAttribute("users", userService.findAllUsers());
            model.addAttribute("rooms", meetingRoomService.findAllRooms());
            bindingResult.getAllErrors();
            return "meeting-update";
        }

        if (id == null || id <= 0) {
            meetingService.addRoom(meetingDto);
            return "redirect:/week";
        } else {
            meetingService.updateRoom(id, meetingDto);
        }
        return "redirect:/week";
    }
}

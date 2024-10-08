package com.nordclan.nikgapon.work_practice_1.controller;

import com.nordclan.nikgapon.work_practice_1.model.MeetingEntity;
import com.nordclan.nikgapon.work_practice_1.service.MeetingRoomService;
import com.nordclan.nikgapon.work_practice_1.service.MeetingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Controller
@RequestMapping("/week")
public class WeekController {
    private final MeetingService meetingService;
    private List<MeetingEntity>[][] schedule;
    private final MeetingRoomService meetingRoomService;

    public WeekController(MeetingService meetingService, MeetingRoomService meetingRoomService) {
        this.meetingService = meetingService;
        this.meetingRoomService = meetingRoomService;
    }


    @GetMapping(value = {"/", "", "/{weeknumber}"})
    public String week(@PathVariable(required = false) Long weeknumber, Model model,
                       @RequestParam(value = "room", required = false) String room_id) {

        Calendar calendar = new GregorianCalendar();
        Locale locale = Locale.UK;

        long curentWeek;
        if (weeknumber == null) {

            weeknumber = 0L;
        }
        curentWeek = weeknumber;
        //System.out.println(curentWeek);

        model.addAttribute("weeknumber", curentWeek);

        calendar.setTime(Date.valueOf(LocalDate.now().plusDays(7 * weeknumber)));
        //if (weeknumber == null || weeknumber == 0) {
            //calendar.setTime(new Date()); нет слов для описания того, чего мне стоила эта ошибка
        //} else {
        //    calendar.setTime(java.sql.Date.valueOf(LocalDate.now().plusDays(7 * weeknumber)));
        //}
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Map<String, String> dateforCurentWeek = new HashMap<>();
        //DateTimeFormatter datefomr = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        SimpleDateFormat datefomr = new SimpleDateFormat("dd.MM.yyyy");
        String startweektext = datefomr.format(calendar.getTime());
        String endweektext;
        LocalDateTime startweek = LocalDateTime.ofInstant(calendar.getTime().toInstant(), calendar.getTimeZone().toZoneId());

        for (int i = 0; i < 7; i++) {
            String nameOfMonth = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale);
            String nameOfDay = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            //System.out.println(dayOfMonth + ": " + calendar.getTime());
            //23: Mon Sep 23 14:25:50 GMT+04:00 2024
            //24: Tue Sep 24 14:25:50 GMT+04:00 2024
            // 5. increase day field; add() will adjust the month if neccessary


            //System.out.println(nameOfDay + ":" + datefomr.format(calendar.getTime()));
            dateforCurentWeek.put(nameOfDay, datefomr.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }
        calendar.add(Calendar.DAY_OF_WEEK, -1);
        endweektext = datefomr.format(calendar.getTime());
        LocalDateTime endweek = LocalDateTime.ofInstant(calendar.getTime().toInstant(), calendar.getTimeZone().toZoneId()).plusDays(1).minusMinutes(1);;
        //System.out.println(endweektext +" "+ endweek);
        //List<MeetingEntity> tets = meetingService.findByTimeInterval(startweek.minusDays(1), endweek.plusDays(1));
        //System.out.println(startweek.toString() + endweek.toString());
        //System.out.println(tets);


        dateforCurentWeek.put("startend", "Неделя:" + startweektext + "—" + endweektext);

        model.addAttribute("weekday", dateforCurentWeek);


        schedule = new ArrayList[7][24];
        for (int i = 0; i < 7; i++) { // day
            for (int j = 0; j < 24; j++) { // half-hours interval
                schedule[i][j] = new ArrayList<>();
            }
        }
        //System.out.println(startweektext +":"+ startweek +"  " + endweektext + ":" + endweek);
        List<MeetingEntity> listAllMeetingsEntity = room_id != null ? meetingService.findByTimeInterval(startweek.minusDays(2), endweek.plusDays(2), Long.parseLong(room_id)) : meetingService.findByTimeInterval(startweek.minusDays(2), endweek.plusDays(2));
        listAllMeetingsEntity.forEach(elemen -> addMeetingToSchedule(elemen.getStarttime().toLocalDateTime(), elemen.getEndtime().toLocalDateTime(), elemen, startweek, endweek));



       /* for (int i = 0; i < schedule.length; i++) {
            System.out.println(Arrays.toString(schedule[i]));
        }*/
        model.addAttribute("rooms", meetingRoomService.findAllRooms());
        model.addAttribute("schedule", schedule);
        return "week";
    }

    @PostMapping(value = {"/", "", "/{weeknumber}"})
    public String toDate(@RequestParam("date") String date){
        return "redirect:/week/" + (Math.floorDiv(java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))), 7));
    }



    public void addMeetingToSchedule(LocalDateTime start, LocalDateTime end, MeetingEntity meeting,LocalDateTime startOfWeek, LocalDateTime endOfWeek) {

        /*if (end.isBefore(startOfWeek) || start.isAfter(endOfWeek)) {
            return;
        }
*/
        //endOfWeek = endOfWeek.plusDays(1).minusMinutes(1);
        LocalDateTime actualStart = start.isBefore(startOfWeek) ? startOfWeek : start;

        LocalDateTime actualEnd = end.isAfter(endOfWeek) ? endOfWeek : end;



        //System.out.println(meeting.getTitle() + "-       " + startOfWeek + " " + start + "  "+actualStart + "jRYJYMXFYBT" + actualEnd);
        //System.out.println(startOfWeek + " - " + endOfWeek);
        for (LocalDateTime time = actualStart; time.isBefore(actualEnd); time = time.plusMinutes(60)) {
            //System.out.println("Сюда");
            int dayOfWeek = time.getDayOfWeek().getValue() - 1;
            //int halfHourIndex = (time.getHour() * 2) + (time.getMinute() / 30);
            int halfHourIndex = (time.getHour());
            //System.out.println("День и Время " + dayOfWeek + " : " + halfHourIndex);

            if (dayOfWeek >= 0 && dayOfWeek < 7 && halfHourIndex >= 0 && halfHourIndex < 24) {
                schedule[dayOfWeek][halfHourIndex].add(meeting);
            }
        }
}}
package com.nordclan.nikgapon.work_practice_1.controller;

import com.nordclan.nikgapon.work_practice_1.model.MeetingEntity;
import com.nordclan.nikgapon.work_practice_1.service.MeetingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/week")
public class WeekController {
    private final MeetingService meetingService;
    private List<MeetingEntity>[][] schedule;

    public WeekController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }


    @GetMapping(value = {"/", "", "/{weeknumber}"})
    public String week(@PathVariable(required = false) Long weeknumber, Model model) {
        Calendar calendar = new GregorianCalendar();
        Locale locale = Locale.UK;
        if (weeknumber == null || weeknumber == 0) {
            calendar.setTime(new Date());
        } else {
            calendar.setTime(java.sql.Date.valueOf(LocalDate.now().plusDays(7 * weeknumber)));
        }
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
        endweektext = datefomr.format(calendar.getTime());
        LocalDateTime endweek = LocalDateTime.ofInstant(calendar.getTime().toInstant(), calendar.getTimeZone().toZoneId());

        //List<MeetingEntity> tets = meetingService.findByTimeInterval(startweek.minusDays(1), endweek.plusDays(1));
        //System.out.println(startweek.toString() + endweek.toString());
        //System.out.println(tets);


        dateforCurentWeek.put("startend", "Неделя:" + startweektext + "—" + endweektext);

        model.addAttribute("weekday", dateforCurentWeek);


        schedule = new ArrayList[7][48];
        for (int i = 0; i < 7; i++) { // day
            for (int j = 0; j < 48; j++) { // half-hours interval
                schedule[i][j] = new ArrayList<>();
            }
        }
        System.out.println(startweektext +":"+ startweek +"  " + endweektext + ":" + endweek);
        List<MeetingEntity> listAllMeetingsEntity = meetingService.findByTimeInterval(startweek.minusDays(0), endweek.plusDays(0)); // todo Разобраться с междневным и меж недельными занятиями, затестировать в хлам
        listAllMeetingsEntity.forEach(elemen -> addMeetingToSchedule(elemen.getStarttime().toLocalDateTime(), elemen.getEndtime().toLocalDateTime(), elemen));



       /* for (int i = 0; i < schedule.length; i++) {
            System.out.println(Arrays.toString(schedule[i]));
        }*/

        model.addAttribute("schedule", schedule);
        return "week";
    }


    public void addMeetingToSchedule(LocalDateTime start, LocalDateTime end, MeetingEntity meeting) {

        for (LocalDateTime time = start; time.isBefore(end); time = time.plusMinutes(30)) {
            int dayOfWeek = time.getDayOfWeek().getValue() - 1;
            int halfHourIndex = (time.getHour() * 2) + (time.getMinute() / 30);

            if (dayOfWeek >= 0 && dayOfWeek < 7 && halfHourIndex >= 0 && halfHourIndex < 48) {
                schedule[dayOfWeek][halfHourIndex].add(meeting);
            }
        }
    }
}
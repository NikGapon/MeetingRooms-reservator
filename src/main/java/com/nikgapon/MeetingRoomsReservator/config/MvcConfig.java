package com.nikgapon.MeetingRoomsReservator.config;
import com.nikgapon.MeetingRoomsReservator.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private MeetingRoomService meetingRoomService;
    @Autowired
    private UserService userService;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry
                .addResourceHandler("/static/js/**")
                .addResourceLocations("classpath:/static/js/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToTimestampConverter());
        registry.addConverter(new MeetingRoomEntityConverter(meetingRoomService));
        registry.addConverter(new UserEntityConverter(userService));
    }
}
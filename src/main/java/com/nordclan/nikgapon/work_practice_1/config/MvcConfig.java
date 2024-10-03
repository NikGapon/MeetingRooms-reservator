package com.nordclan.nikgapon.work_practice_1.config;
import com.nordclan.nikgapon.work_practice_1.service.MeetingRoomEntityConverter;
import com.nordclan.nikgapon.work_practice_1.service.StringToTimestampConverter;
import com.nordclan.nikgapon.work_practice_1.service.UserEntityConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/css/**")
                .addResourceLocations("classpath:/static/css/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToTimestampConverter());
        registry.addConverter(new MeetingRoomEntityConverter());
        registry.addConverter(new UserEntityConverter());
    }
}
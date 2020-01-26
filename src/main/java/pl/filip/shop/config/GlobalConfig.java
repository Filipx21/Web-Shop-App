package pl.filip.shop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
public class GlobalConfig {



//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }

//    @Bean
//    public Formatter<LocalDate> localDateFormatter() {
//        return new Formatter<>() {
//            @Override
//            public String print(LocalDate object, Locale locale) {
//                return DateTimeFormatter.ISO_DATE.format(object);
//            }
//
//            @Override
//            public LocalDate parse(String text, Locale locale) {
//                return LocalDate.parse(text, DateTimeFormatter.ISO_DATE);
//            }
//        };
//    }


}

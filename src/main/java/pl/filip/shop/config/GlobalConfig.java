package pl.filip.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class GlobalConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

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

package CTDTFullStack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import CTDTFullStack.Models.Cookie;


@Configuration
public class AppConfig {

    @Bean
    @Scope("session")
    public Cookie cookieStore() {
        return new Cookie();
    }
}

package pl.akademiaspecjalistowit.documentflowmanagementservice.document.infrastructure;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.akademiaspecjalistowit.documentflowmanagementservice.document.user.repository.UserRepository;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<UsernameHeaderFilter> usernameHeaderFilterRegistration(UsernameHeaderFilter usernameHeaderFilter) {
        FilterRegistrationBean<UsernameHeaderFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(usernameHeaderFilter);
        registration.addUrlPatterns("/api/*");
        registration.setName("usernameHeaderFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public UsernameHeaderFilter usernameHeaderFilter(UserRepository userRepository) {
        return new UsernameHeaderFilter(userRepository);
    }
}

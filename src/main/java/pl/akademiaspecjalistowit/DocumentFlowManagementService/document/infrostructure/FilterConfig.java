package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.infrostructure;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<UsernameHeaderFilter> usernameHeaderFilterRegistration(UsernameHeaderFilter usernameHeaderFilter) {
        FilterRegistrationBean<UsernameHeaderFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(usernameHeaderFilter);
        registration.addUrlPatterns("/*"); // Dla wszystkich endpointów
        registration.setName("usernameHeaderFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public UsernameHeaderFilter usernameHeaderFilter() {
        return new UsernameHeaderFilter();
    }
}

package com.favorites.comm.config;//package com.favorites.comm.config;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.favorites.comm.filter.SecurityFilter;
//
//@Configuration
//public class WebFilter {
//
//    @Bean
//    public FilterRegistrationBean filterRegistration() {
//
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new SecurityFilter());
//        registration.addUrlPatterns("/*");
//        registration.addInitParameter("paramName", "paramValue");
//        registration.setName("MyFilter");
//        registration.setOrder(1);
//        return registration;
//    }
//
//}
//
//
//

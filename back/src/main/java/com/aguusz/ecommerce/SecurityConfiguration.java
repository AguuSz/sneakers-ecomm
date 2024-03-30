package com.aguusz.ecommerce;

import com.aguusz.ecommerce.controllers.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(
                    CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("*").allowedHeaders("*").allowedOrigins("*");
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests().anyRequest().permitAll();

//                antMatchers(HttpMethod.POST, Constants.URL_LOGIN).permitAll()
//                .antMatchers("/api-docs/**").permitAll()
//                .antMatchers("/swagger-ui.html").permitAll()
//                .antMatchers("/swagger-ui/**").permitAll()
//                .antMatchers("/ws/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
}

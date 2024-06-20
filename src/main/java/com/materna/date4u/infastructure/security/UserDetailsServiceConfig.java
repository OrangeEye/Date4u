package com.materna.date4u.infastructure.security;

import com.materna.date4u.infastructure.repositories.UnicornRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration(proxyBeanMethods = false)
public class UserDetailsServiceConfig {

    @Bean
    UserDetailsService myUserDetailsService(UnicornRepository repository) {
        return (String email) -> repository.findByEmail(email)
                .map(unicorn -> User.builder()
                        .username(unicorn.getEmail())
                        .password(unicorn.getPassword())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("user konnte nicht gefunden werden: " + email));

    }
}

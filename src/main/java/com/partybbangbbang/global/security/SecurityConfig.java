package com.partybbangbbang.global.security;

import com.partybbangbbang.global.exception.ApiExceptionHandlingFilter;
import com.partybbangbbang.global.security.matcher.CustomRequestMatcher;
import com.partybbangbbang.global.security.provider.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ApiExceptionHandlingFilter apiExceptionHandlingFilter;
    private final CustomRequestMatcher customRequestMatcher;

    @Bean
    public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
        http.securityMatchers(matcher -> matcher.requestMatchers(
                        customRequestMatcher.customEndpoints()))
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .addFilterBefore(apiExceptionHandlingFilter, UsernamePasswordAuthenticationFilter.class);

        return commonHttpSecurity(http).build();
    }

    private HttpSecurity commonHttpSecurity(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(configurer -> corsConfigurationSource())
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(CustomAuthenticationProvider authenticationProvider) {
        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("HEAD", "POST", "GET", "DELETE", "PUT"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

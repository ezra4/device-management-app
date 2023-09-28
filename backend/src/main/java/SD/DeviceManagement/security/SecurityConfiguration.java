/*
package SD.DeviceManagement.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final PasswordConfig passwordConfig;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        final GrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
        final GrantedAuthority clientAuthority = new SimpleGrantedAuthority("ROLE_CLIENT");
        http
                .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/devices").hasRole("ADMIN")
                .requestMatchers("/devices/**").hasRole("ADMIN")
                .requestMatchers("/client").hasAnyRole("ADMIN","CLIENT")
                .requestMatchers("/client/**").hasAnyRole("ADMIN","CLIENT")
                .anyRequest().permitAll()
                .and()
                .authenticationProvider(authenticationProvider)
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler((request, response, authentication) -> {
                    response.addCookie(Context);
                })
                .and()
                .logout()
                .logoutSuccessUrl("/login");
        return http.build();
    }
}*/

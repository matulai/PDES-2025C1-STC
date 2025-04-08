package SeguiTusCompras.Security;

import SeguiTusCompras.model.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
     private final JwtAuthFilter jwtAuthFilter;
     // private final AuthenticationProvider authprovider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //TODO
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/admin/**").hasAuthority(Role.Admin.name())
                                .requestMatchers("/client/**").hasAuthority(Role.Client.name())
                                .anyRequest().authenticated()
                        )
                        .addFilterBefore(new JwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                        .sessionManagement(sessionManager -> sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .build();
    }
}

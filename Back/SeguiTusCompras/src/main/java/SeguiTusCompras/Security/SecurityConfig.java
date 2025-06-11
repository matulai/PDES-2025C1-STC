package SeguiTusCompras.Security;

import SeguiTusCompras.model.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    private final SecurityCorsConfig corsConfigurationSource;


    public SecurityConfig(JwtAuthFilter jwtAuthFilter,  SecurityCorsConfig corsConfig) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.corsConfigurationSource = corsConfig;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers("/auth/**",
                                        "/products/**",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui.html").permitAll()
                                .requestMatchers("/admin/**").hasAuthority(Role.Admin.name())
                                .requestMatchers("/client/**").hasAuthority(Role.Client.name())
                                .requestMatchers("/user/**").hasAnyAuthority(Role.Client.name(), Role.Admin.name())
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(sessionManager -> sessionManager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}

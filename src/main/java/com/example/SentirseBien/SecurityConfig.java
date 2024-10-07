package com.example.SentirseBien;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Recursos estáticos accesibles sin autenticación
                .requestMatchers("/img/**", "/css/**", "/js/**", "/lib/**", "/mail/**", "/scss/**").permitAll()
                
                // Rutas accesibles sin autenticación
                .requestMatchers("/", "/index", "/login", "/cliente/**", "/servicios", "/noticias", "/empleo", "/contacto").permitAll()
                
                // Rutas solo para empleados
                .requestMatchers("/empleado/**", "/utilidades", "/reservas", "/consulta/editar/**").hasAuthority("EMPLEADO")
                
                // Rutas solo para clientes
                .requestMatchers("/hacerreserva").hasAuthority("CLIENTE")
                
                // Cualquier otra solicitud requiere autenticación
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")  // Página personalizada de login
                .defaultSuccessUrl("/", true)  // Redirección tras el login exitoso
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/403")  // Página de error de acceso denegado
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")  // Redirección tras el logout
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
